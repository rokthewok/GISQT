package gissystem.factories;

import gissystem.commands.CountWhatIsInCommand;
import gissystem.commands.LongWhatIsInCommand;
import gissystem.commands.WhatIsInCommand;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;
import gissystem.models.GeographicPoint;

/**
 * Factory method which handles the creation of the three kinds of what-is-in commands.
 * @author John
 *
 */
public class WhatIsInCommandFactory implements ICommandFactory {

	@Override
	public ICommand build(String rawCommand) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "-l" ) ) {
			// the verbose option is specified
			String [] latAndLong = parts[2].split( " " );
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( latAndLong[0] ), GeographicCoordinateFactory.createCoordinate( latAndLong[1] ) );
			Long halfHeight = new Long( parts[3] );
			Long halfWidth = new Long( parts[4] );
			return new LongWhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight );
		} else if( parts[1].equals( "-c" ) ) {
			// the count option is specified
			String [] latAndLong = parts[2].split( " " );
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( latAndLong[0] ), GeographicCoordinateFactory.createCoordinate( latAndLong[1] ) );
			Long halfHeight = new Long( parts[3] );
			Long halfWidth = new Long( parts[4] );
			return new CountWhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight );
		} else {
			// the basic WhatIsInCommand
			String [] latAndLong = parts[1].split( " " );
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( latAndLong[0] ), GeographicCoordinateFactory.createCoordinate( latAndLong[1] ) );
			Long halfHeight = new Long( parts[2] );
			Long halfWidth = new Long( parts[3] );
			return new WhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight );
		}
	}
}
