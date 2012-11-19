package gissystem.factories;

import gissystem.commands.ImportCommand;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;

/**
 * Factory method which creates an ImportCommand.
 * @author John
 *
 */
public class ImportCommandFactory implements ICommandFactory {

	@Override
	public ICommand build( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		return new ImportCommand( parts[1] );
	}

}
