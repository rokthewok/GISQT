package gissystem.factories;

import gissystem.commands.CountWhatIsAtCommand;
import gissystem.commands.LongWhatIsAtCommand;
import gissystem.commands.WhatIsAtCommand;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

/**
 * Factory method which handles creating the three types of WhatIsAt commands
 * @author John
 *
 */
public class WhatIsAtCommandFactory implements ICommandFactory {

	@Override
	public ICommand build(String rawCommand) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "-l" ) ) {
			// the verbose option is specified
			String [] latAndLong = parts[2].split( " " );
			return new LongWhatIsAtCommand( latAndLong[0], latAndLong[1] );
		} else if( parts[1].equals( "-c" ) ) {
			// the count option is specified
			String [] latAndLong = parts[2].split( " " );
			return new CountWhatIsAtCommand( latAndLong[0], latAndLong[1] );
		} else {
			// the basic WhatIsAtCommand
			String [] latAndLong = parts[1].split( " " );
			return new WhatIsAtCommand( latAndLong[0], latAndLong[1] );
		}
	}
}
