package gissystem.commands.helpers;

import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

/**
 * Used to format Strings for verbose log output in the commands.
 * @see IFormatter
 * @author Captain John
 *
 */
public class VerboseFormatter implements IFormatter {
	
	/**
	 * Implementation of IFormatter method.
	 * @see IFormatter
	 */
	@Override
	public String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\t" );
		
		appendItem( "offset", offset.toString(), sb );
		appendItem( "feature ID", feature.getId().toString(), sb );
		appendItem( "feature name", feature.getName(), sb );
		appendItem( "classification", feature.getClassification(), sb );
		appendItem( "state", feature.getAlphabeticStateCode(), sb );
		appendItem( "county", feature.getCountyName(), sb );
		appendItem( "latitude", feature.getPrimaryLatitude(), sb );
		appendItem( "longitude", feature.getPrimaryLongitude(), sb );
		appendItem( "Source lat", feature.getSourceLatitude(), sb );
		appendItem( "Source long", feature.getSourceLongitude(), sb );
		appendItem( "elevation (m)", feature.getElevationInMeters(), sb );
		appendItem( "quadrangle", feature.getMapName(), sb );
		appendItem( "date created", feature.getDateCreated(), sb );
		appendItem( "date edited", feature.getDateEdited(), sb );
		
		return sb.toString();
	}
	
	/*
	 * Helper functions to check for null fields. If the field is not null, add it to the StringBuilder.
	 */
	private void appendItem( String label, String item, StringBuilder sb ) {
		if( item != null ) {
			sb.append( label + ":" );
			for( int i = 0; i < 21 - label.length(); i++ ) {
				sb.append( " " );
			}
			sb.append( item );
			sb.append( "\n\t" );
		}
	}
	
	private void appendItem( String label, Object object, StringBuilder sb ) {
		if( object != null ) {
			appendItem( label, object.toString(), sb );
		}
	}
}
