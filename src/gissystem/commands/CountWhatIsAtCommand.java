package gissystem.commands;

import gissystem.factories.GeographicCoordinateFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.models.GeographicPoint;

/**
 * The object representation of the "what_is_at -c" command and option. Counts the number of offsets associated with
 * a given pair of geographic coordinates.
 * @author John Q. Ruffer
 *
 */
public class CountWhatIsAtCommand implements ICommand {
	private String rawLatitude;
	private String rawLongitude;
	
	/**
	 * ctor.
	 * @param rawLatitude The String representation of a latitude coordinate, in DMS format.
	 * @param rawLongitude The String representation of a longitude coordinate, in DMS format.
	 */
	public CountWhatIsAtCommand( String rawLatitude, String rawLongitude ) {
		this.rawLatitude = rawLatitude;
		this.rawLongitude = rawLongitude;
	}
	
	/**
	 * Executes the what_is_at -c command.
	 */
	/*
	 *  1. creates a point from a latitude and longitude pair.
	 * 2. asks the quad tree for a list of values corresponding to the point; gets the size of the list
	 * 3. writes to the log the size of the list, which is the number of offsets at that point
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( this.rawLatitude ),
				GeographicCoordinateFactory.createCoordinate( this.rawLongitude ) );
		
		controller.getLogger().writeToLog( "The number of offsets found at " + this.rawLongitude + ", " + this.rawLatitude + " is: " );
		// find the offests in the quadtree corresponding to the given point, print the size of the list of offsets
		controller.getLogger().writeToLog( ( new Integer( controller.getQuadTreeController().findInQuadTree( point ).size() ) ).toString() );
	}
}
