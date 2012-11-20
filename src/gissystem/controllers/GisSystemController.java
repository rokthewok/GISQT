package gissystem.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import gissystem.helpers.RawCommandParser;
import gissystem.helpers.io.FileLogger;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class GisSystemController {
	private String logFilename;
	private String commandFilename;
	private String databaseFilename;
	
	private IDataAccessController dataAccessController;
	
	public GisSystemController() {
		this.dataAccessController = null;
	}
	
	public void doWork() {
		if( this.dataAccessController == null ) {
			return;
		}
		
		RawCommandParser commandParser = new RawCommandParser();
		
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
	
	public void doSetup( String [] args ) {
		parseArguments( args );
		
		RandomAccessFile commandFile = null;
		RandomAccessFile logFile = null;
		try {
			commandFile = new RandomAccessFile( this.commandFilename, "r" );
			logFile = new RandomAccessFile( this.logFilename, "rw" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File databaseFile = new File( this.databaseFilename );

		if( commandFile == null || logFile == null ) {
			System.out.println( "Something went wrong with the command file or log file... please try again. Exiting now." );
			System.exit( -1 );
		}
		
		this.dataAccessController = new DataAccessController( databaseFile, commandFile, new FileLogger( logFile ) );
	}
	
	private void parseArguments( String [] args ) {
		if( args.length < 3 ) {
			System.out.println( "Sorry, please provide the necessary number of arguments. Exiting now." );
			System.exit( -1 );
		}
		
		this.databaseFilename = args[0];
		this.commandFilename = args[1];
		this.logFilename = args[2];
	}
}
