package gissystem.commands.helpers;

import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

/**
 * Formats information relevant to "what_is_in" command into a String for logging.
 * @see IFormatter
 * @author John Ruffer
 *
 */
public class WhatIsInFormatter implements IFormatter {
	
	/**
	 * Implementation of IFormatter method.
	 * @see IFormatter
	 */
	@Override
	public String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\t" );
		sb.append( offset );
		sb.append( ":\t\t" );
		sb.append( feature.getName() );
		sb.append( "  " );
		sb.append( feature.getAlphabeticStateCode() );
		sb.append( "  " );
		sb.append( feature.getPrimaryLatitude().toString() );
		sb.append( "  " );
		sb.append( feature.getPrimaryLongitude().toString() );
		
		return sb.toString();
	}
}
