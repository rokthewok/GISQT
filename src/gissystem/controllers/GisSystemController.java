package gissystem.controllers;

import gissystem.helpers.RawCommandParser;
import gissystem.interfaces.ICommand;

public class GisSystemController {
	private DataAccessController dataAccessController;
	
	public GisSystemController( DataAccessController dataAccessController ) {
		this.dataAccessController = dataAccessController;
	}
	
	public void doWork() {
		RawCommandParser commandParser = new RawCommandParser();
		
		while( true ) {
			String rawCommand = this.dataAccessController.getNextCommandLine();
			if( rawCommand.charAt( 0 ) != ';' ) {				
				ICommand command = commandParser.parseCommand( rawCommand );
				command.execute( this.dataAccessController );
			}
		}
	}
}
