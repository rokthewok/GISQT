package gissystem.commands;

import gissystem.models.GeographicFeature;

public class LongWhatIsInCommand extends WhatIsInCommand {

	public LongWhatIsInCommand( long xMin, long xMax, long yMin, long yMax ) {
		super(xMin, xMax, yMin, yMax);
	}

	@Override
	protected String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\n" );
		
		appendItem( "offset", offset.toString(), sb );
		appendItem( "feature ID", feature.getId().toString(), sb );
		appendItem( "feature name", feature.getName(), sb );
		appendItem( "classification", feature.getClassification(), sb );
		appendItem( "state", feature.getAlphabeticStateCode(), sb );
		appendItem( "county", feature.getCountyName(), sb );
		appendItem( "latitude", feature.getPrimaryLatitude().toString(), sb );
		appendItem( "longitude", feature.getPrimaryLongitude().toString(), sb );
		appendItem( "elevation (m)", feature.getElevationInMeters().toString(), sb );
		appendItem( "quadrangle", feature.getMapName(), sb );
		
		return sb.toString();
	}
	
	private void appendItem( String label, String item, StringBuilder sb ) {
		if( item != null ) {
			sb.append( "\t" );
			sb.append( label + ":" );
			for( int i = 0; i < 21 - label.length(); i++ ) {
				sb.append( " " );
			}
			sb.append( item );
			sb.append( "\n" );
		}
	}
}
