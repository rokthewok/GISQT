package gissystem.controllers;

import gissystem.helpers.RawCommandParser;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class GisSystemController {
	private IDataAccessController dataAccessController;
	
	public GisSystemController( IDataAccessController dataAccessController ) {
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
