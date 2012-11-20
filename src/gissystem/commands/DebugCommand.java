package gissystem.commands;

import gissystem.helpers.Debug;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class DebugCommand implements ICommand {
	Debug type;
	
	public DebugCommand( Debug type ) {
		this.type = type;
	}

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
