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

public class WhatIsAtCommand implements ICommand {
	private String rawLatitude;
	private String rawLongitude;
	private IFormatter formatter;
	
	public WhatIsAtCommand( String rawLatitude, String rawLongitude ) {
		this( rawLatitude, rawLongitude, new WhatIsAtFormatter() );
	}
	
	public WhatIsAtCommand( String rawLatitude, String rawLongitude, IFormatter formatter ) {
		this.rawLatitude = rawLatitude;
		this.rawLongitude = rawLongitude;
		this.formatter = formatter;
	}

	@Override
	public void execute( IDataAccessController controller ) {
		GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( rawLatitude ),
				GeographicCoordinateFactory.createCoordinate( this.rawLongitude ) );
		
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( point );
		
		controller.getLogger().writeToLog( "Found at " + this.rawLongitude + ", " + this.rawLatitude + ":\n" );
		
		// if offsets contains nothing, then there is nothing at that coordinate. Else, loop through all the offsets found.
		if( offsets.isEmpty() ) {
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

//	protected String formatFeatureOutput( Long offset, GeographicFeature feature ) {
//		StringBuilder sb = new StringBuilder();
//		sb.append( "\toffset: " );
//		sb.append( offset );
//		sb.append( "\n\tfeature name: " );
//		sb.append( feature.getName() );
//		sb.append( "\n\tfeature county: " );
//		sb.append( feature.getCountyName() );
//		sb.append( "\n\tfeature state: " );
//		sb.append( feature.getAlphabeticStateCode() );
//		sb.append( "\n" );
//		
//		return sb.toString();
//	}
}
