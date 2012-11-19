package gissystem.factories;

import gissystem.commands.DebugCommand;
import gissystem.helpers.Debug;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

/**
 * Factory method which produces the appropriate Debug command.
 * @author John
 *
 */
public class DebugCommandFactory implements ICommandFactory {

	@Override
	public ICommand build( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "quad" ) ) {
			return new DebugCommand( Debug.QUADTREE );
		} else if( parts[1].equals( "hash" ) ) {
			return new DebugCommand( Debug.HASHTABLE );
		} else {
			return new DebugCommand( Debug.BUFFERPOOL );
		}
	}

}
