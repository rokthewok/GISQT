package gissystem.commands;

import java.util.List;

import gissystem.commands.helpers.WhatIsAtFormatter;
import gissystem.factories.GeographicCoordinateFactory;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;
import gissystem.models.GeographicPoint;

/**
 * Object representation of the "what_is_at" command. Accepts an IFormatter which prints the results with the
 * appropriate level of verbosity.
 * @author John "John" Ruffer
 *
 */
public class WhatIsAtCommand implements ICommand {
	private String rawLatitude;
	private String rawLongitude;
	private IFormatter formatter;
	
	/**
	 * ctor.
	 * @param rawLatitude The String representation of a latitude coordinate, in DMS format.
	 * @param rawLongitude The String representation of a longitude coordinate, in DMS format.
	 */
	public WhatIsAtCommand( String rawLatitude, String rawLongitude ) {
		this( rawLatitude, rawLongitude, new WhatIsAtFormatter() );
	}
	
	/**
	 * ctor.
	 * @param rawLatitude The String representation of a latitude coordinate, in DMS format.
	 * @param rawLongitude The String representation of a longitude coordinate, in DMS format.
	 * @param formatter The formatter for logfile output.
	 */
	public WhatIsAtCommand( String rawLatitude, String rawLongitude, IFormatter formatter ) {
		this.rawLatitude = rawLatitude;
		this.rawLongitude = rawLongitude;
		this.formatter = formatter;
	}

	/**
	 * Executes the "what_is_at", with possible options for "-l".
	 */
	/*
	 * 1. create GeographicPoint (IPoint) object from two GeographicCoordinate objects
	 * 2. git a list of offests from the quadtree using the point object
	 * 3. for each offset in offsets:
	 * 		4. get record from db
	 * 		5. build feature from record
	 * 		6. log using the formatter member
	 * 7. endfor
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( rawLatitude ),
				GeographicCoordinateFactory.createCoordinate( this.rawLongitude ) );
		
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( point );
		
		controller.getLogger().writeToLog( "Found at " + this.rawLongitude + ", " + this.rawLatitude + ":\n" );
		
		// if offsets contains nothing, then there is nothing at that coordinate. Else, loop through all the offsets found.
		if( offsets == null ) {
			controller.getLogger().writeToLog( "\tnothing at specified coordinates\n" );
		} else {
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( this.formatter.formatFeatureOutput( offset, feature ) );
				controller.getLogger().writeToLog( "\n" );
			}
		}
	}
}
