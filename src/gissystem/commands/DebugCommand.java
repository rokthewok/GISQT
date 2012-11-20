package gissystem.commands;

import gissystem.helpers.Debug;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

/**
 * The object representation of the "debug" command.
 * @author John "Rico Suave" Ruffer
 *
 */
public class DebugCommand implements ICommand {
	private Debug type;
	
	/**
	 * ctor.
	 * @param type the Debug type of the command.
	 * @see Debug
	 */
	public DebugCommand( Debug type ) {
		this.type = type;
	}

	/**
	 * Executes the "debug" command.
	 */ 
	/*
	 * 1. evalute the Debug type
	 * 2. write to the log a visualization of the appropriate data structure. 
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		switch( this.type ) {
		case QUADTREE:
			controller.getLogger().writeToLog( controller.getQuadTreeController().getQuadTreeToString() );
			break;
		case HASHTABLE:
			controller.getLogger().writeToLog( "Display format is: <table slot #>: {<record key>, <record offsets>}\n" );
			controller.getLogger().writeToLog( controller.getHashTableController().getHashTableToString() );
			break;
		case BUFFERPOOL:
			controller.getLogger().writeToLog( controller.getDatabaseController().getBufferPoolToString() );
			break;
		default:
			break;
		}
	}
}
