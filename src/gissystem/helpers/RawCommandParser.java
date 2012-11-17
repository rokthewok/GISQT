package gissystem.helpers;

import gissystem.factories.WorldCommandFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

import java.util.HashMap;

public class RawCommandParser {
	private HashMap<String, ICommandFactory> factories;
	
	public RawCommandParser() {
		factories = new HashMap<String, ICommandFactory>();
		factories.put( "what_is_at", new WorldCommandFactory() );
		// etc
	}
	
	public ICommand parseCommand( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		if( !this.factories.containsKey( parts[0] ) ) {
			return null;
		}
		
		return this.factories.get( parts[0] ).build( rawCommand );
	}
}
