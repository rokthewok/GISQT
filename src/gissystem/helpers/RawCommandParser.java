package gissystem.helpers;

import gissystem.factories.DebugCommandFactory;
import gissystem.factories.ImportCommandFactory;
import gissystem.factories.WhatIsAtCommandFactory;
import gissystem.factories.WhatIsCommandFactory;
import gissystem.factories.WhatIsInCommandFactory;
import gissystem.factories.WorldCommandFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

import java.util.HashMap;

/**
 * Handles delegating the creation of a Command object to the appropriate factory.
 * @author John Ruffer, I swear
 *
 */
public class RawCommandParser {
	private HashMap<String, ICommandFactory> factories;
	
	public RawCommandParser() {
		factories = new HashMap<String, ICommandFactory>();
		
		// add all the command factories to the hashmap
		factories.put( "world", new WorldCommandFactory() );
		factories.put( "import", new ImportCommandFactory() );
		factories.put( "what_is_at", new WhatIsAtCommandFactory() );
		factories.put( "what_is", new WhatIsCommandFactory() );
		factories.put( "what_is_in", new WhatIsInCommandFactory() );
		factories.put( "debug", new DebugCommandFactory() );
	}
	
	public ICommand parseCommand( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		// the necessary factory does not exist
		if( !this.factories.containsKey( parts[0] ) ) {
			return null;
		}
		
		// get the appropriate factory in the hashmap and build the command
		return this.factories.get( parts[0] ).build( rawCommand );
	}
}
