package gissystem.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import gissystem.helpers.RawCommandParser;
import gissystem.helpers.io.FileLogger;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

/**
 * Main controller for the GIS System. Runs the main loop and sets up resources.
 * @author John Ruffer
 *
 */
public class GisSystemController {
	private String logFilename;
	private String commandFilename;
	private String databaseFilename;
	private IDataAccessController dataAccessController;
	
	public GisSystemController() {
		this.dataAccessController = null;
	}
	
	/**
	 * Main loop for the GIS System. doSetup() MUST be called before this method.
	 */
	public void doWork() {
		if( this.dataAccessController == null ) {
			return;
		}
		
		RawCommandParser commandParser = new RawCommandParser();
		
		writeHeader();
		
		int count = 1;
		while( true ) {
			String rawCommand = this.dataAccessController.getNextCommandLine();
			
			if( rawCommand.equals( "quit\t" ) ) {
				break;
			} else if( rawCommand.charAt( 0 ) != ';' ) {				
				ICommand command = commandParser.parseCommand( rawCommand );
				if( command != null ) {
					this.dataAccessController.getLogger().writeToLog( "Executing command " + count + ": " + rawCommand + "\n\n" );
					command.execute( this.dataAccessController );
					this.dataAccessController.getLogger().writeToLog( "\n\n-----------------------------------------------------------------\n\n" );
				} else {
					this.dataAccessController.getLogger().writeToLog( "Bad command in file: " + rawCommand );
					this.dataAccessController.getLogger().writeToLog( "\n\n-----------------------------------------------------------------\n\n" );
				}
				
				count++;
			}
		}
		
		this.dataAccessController.getLogger().writeToLog( "Command sequence quit. Exiting now." );
	}
	
	/**
	 * Sets up the GIS System to do the appropriate work. Parses command line arguments and creates necessary controller objects.
	 * @param args The command line arguments. Must contain 
	 */
	public void doSetup( String [] args ) {
		parseArguments( args );
		
		RandomAccessFile commandFile = null;
		RandomAccessFile logFile = null;
		try {
			// create RAF's for the command and log files.
			commandFile = new RandomAccessFile( this.commandFilename, "r" );
			logFile = new RandomAccessFile( this.logFilename, "rw" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// create an object representation of the database file.
		File databaseFile = new File( this.databaseFilename );

		if( commandFile == null || logFile == null ) {
			System.out.println( "Something went wrong with the command file or log file... please try again. Exiting now." );
			System.exit( -1 );
		}
		
		// create the data access controller using the three files.
		this.dataAccessController = new DataAccessController( databaseFile, commandFile, new FileLogger( logFile ) );
	}
	
	/*
	 * Parses out the command line arguments into the appropriate filenames.
	 */
	private void parseArguments( String [] args ) {
		if( args.length < 3 ) {
			System.out.println( "Sorry, please provide the necessary number of arguments. Exiting now." );
			System.exit( -1 );
		}
		
		this.databaseFilename = args[0];
		this.commandFilename = args[1];
		this.logFilename = args[2];
	}
	
	/*
	 * Writes a header out to the log file.
	 */
	private void writeHeader() {
		this.dataAccessController.getLogger().writeToLog( "GIS System\n" );
		this.dataAccessController.getLogger().writeToLog( "database: " );
		this.dataAccessController.getLogger().writeToLog( this.databaseFilename );
		this.dataAccessController.getLogger().writeToLog( "\ncommands: " );
		this.dataAccessController.getLogger().writeToLog( this.commandFilename );
		this.dataAccessController.getLogger().writeToLog( "\ncaptain's log: " );
		this.dataAccessController.getLogger().writeToLog( this.logFilename );
		this.dataAccessController.getLogger().writeToLog( "\nstar date:\n\n\n" ); //TODO add current datetime
	}
}
