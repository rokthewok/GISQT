package gissystem.factories;

import gissystem.commands.CountWhatIsCommand;
import gissystem.commands.WhatIsCommand;
import gissystem.commands.helpers.VerboseFormatter;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

/**
 * Factory method which handles creating the three types of what-is commands
 * @author John
 *
 */
public class WhatIsCommandFactory implements ICommandFactory {

	@Override
	public ICommand build( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "-l" ) ) {
			// the verbose option is specified
			return new WhatIsCommand( parts[2], parts[3], new VerboseFormatter() );
		} else if( parts[1].equals( "-c" ) ) {
			// the count option is specified
			return new CountWhatIsCommand( parts[2], parts[3] );
		} else {
			// the basic WhatIsCommand
			return new WhatIsCommand( parts[1], parts[2] );
		}
	}
}
