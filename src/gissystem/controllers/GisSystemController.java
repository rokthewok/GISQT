package gissystem.controllers;

import gissystem.helpers.RawCommandParser;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class GisSystemController {
	private String logFilename;
	private String commandFilename;
	private String databaseFilename;
	
	private IDataAccessController dataAccessController;
	
	public GisSystemController( IDataAccessController dataAccessController ) {
		this.dataAccessController = dataAccessController;
	}
	
	public void doWork() {
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
		
	}
	
	private void parseArguments( String [] args ) {
		if( args.length < 3 ) {
			System.out.println( "Sorry, please provide the necessary number of arguments." );
			System.exit( -1 );
		}
	}
}
