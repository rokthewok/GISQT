package gissystem.factories;

import gissystem.commands.CountWhatIsInCommand;
import gissystem.commands.WhatIsInCommand;
import gissystem.commands.helpers.VerboseFormatter;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.ICommandFactory;
import gissystem.models.GeographicPoint;

/**
 * Factory method which handles the creation of the three kinds of what-is-in commands.
 * @see WhatIsInCommand
 * @see CountWhatIsInCommand
 * @author John
 *
 */
public class WhatIsInCommandFactory implements ICommandFactory {

	@Override
	public ICommand build(String rawCommand) {
		String [] parts = rawCommand.split( "\t" );
		
		if( parts[1].equals( "-l" ) ) {
			// the verbose option is specified
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( parts[2] ), GeographicCoordinateFactory.createCoordinate( parts[3] ) );
			Long halfHeight = new Long( parts[4] );
			Long halfWidth = new Long( parts[5] );
			return new WhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight, new VerboseFormatter() );
		} else if( parts[1].equals( "-c" ) ) {
			// the count option is specified
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( parts[2] ), GeographicCoordinateFactory.createCoordinate( parts[3] ) );
			Long halfHeight = new Long( parts[4] );
			Long halfWidth = new Long( parts[5] );
			return new CountWhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight );
		} else {
			// the basic WhatIsInCommand
			GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( parts[1] ), GeographicCoordinateFactory.createCoordinate( parts[2] ) );
			Long halfHeight = new Long( parts[3] );
			Long halfWidth = new Long( parts[4] );
			return new WhatIsInCommand( point.getX() - halfWidth, point.getX() + halfWidth, point.getY() - halfHeight, point.getY() + halfHeight );
		}
	}
}
