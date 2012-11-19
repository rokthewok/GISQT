package gissystem.commands;

import java.util.List;

import gissystem.factories.GeographicCoordinateFactory;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.models.GeographicFeature;
import gissystem.models.GeographicPoint;

public class WhatIsAtCommand implements ICommand {
	private String rawLatitude;
	private String rawLongitude;
	
	public WhatIsAtCommand( String rawLatitude, String rawLongitude ) {
		this.rawLatitude = rawLatitude;
		this.rawLongitude = rawLongitude;
	}

	@Override
	public void execute( IDataAccessController controller ) {
		GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( rawLatitude, "0.0" ),
				GeographicCoordinateFactory.createCoordinate( this.rawLongitude, "0.0" ) );
		
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( point );
		
		controller.getLogger().writeToLog( "Found at " + this.rawLongitude + ", " + this.rawLatitude + ":\n" );
		
		// if offsets contains nothing, then there is nothing at that coordinate. Else, loop through all the offsets found.
		if( offsets.isEmpty() ) {
			controller.getLogger().writeToLog( "\tnothing at specified coordinates\n" );
		} else {
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( formatFeatureOutput( feature ) );
				controller.getLogger().writeToLog( "\n" );
			}
		}
	}

	protected String formatFeatureOutput( GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\tfeature name: " );
		sb.append( feature.getName() );
		sb.append( "\n\tfeature county: " );
		sb.append( feature.getCountyName() );
		sb.append( "\n\tfeature state: " );
		sb.append( feature.getAlphabeticStateCode() );
		sb.append( "\n" );
		
		return sb.toString();
	}
}
