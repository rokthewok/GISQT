package gissystem.factories;

import gissystem.commands.CountWhatIsAtCommand;
import gissystem.commands.WhatIsAtCommand;
import gissystem.commands.helpers.VerboseFormatter;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

/**
 * Factory method which handles creating the three types of WhatIsAt commands.
 * @see WhatIsAtCommand
 * @see CountWhatIsAtCommand
 * @author John Q Ruffer
 *
 */
public class WhatIsAtCommandFactory implements ICommandFactory {

	@Override
	public ICommand build(String rawCommand) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "-l" ) ) {
			// the verbose option is specified
			return new WhatIsAtCommand( parts[2], parts[3], new VerboseFormatter() );
		} else if( parts[1].equals( "-c" ) ) {
			// the count option is specified
			return new CountWhatIsAtCommand( parts[2], parts[3] );
		} else {
			// the basic WhatIsAtCommand
			return new WhatIsAtCommand( parts[1], parts[2] );
		}
	}
}
