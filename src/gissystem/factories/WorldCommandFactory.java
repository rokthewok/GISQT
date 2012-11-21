package gissystem.factories;

import gissystem.commands.WorldCommand;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;
import gissystem.models.GeographicCoordinate;

/**
 * Factory method that creates a {@link WorldCommand} from a raw command String.
 * @author John
 *
 */
public class WorldCommandFactory implements ICommandFactory {

	public ICommand build( String rawCommand ) {
		String [] parts = rawCommand.split( "\t" );
		
		GeographicCoordinate west = GeographicCoordinateFactory.createCoordinate( parts[1] );
		GeographicCoordinate east = GeographicCoordinateFactory.createCoordinate( parts[2] );
		GeographicCoordinate south = GeographicCoordinateFactory.createCoordinate( parts[3] );
		GeographicCoordinate north = GeographicCoordinateFactory.createCoordinate( parts[4] );
		
		return new WorldCommand( west.toSeconds(), east.toSeconds(), south.toSeconds(), north.toSeconds() );
	}

}
