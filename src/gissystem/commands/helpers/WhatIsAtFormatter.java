package gissystem.commands.helpers;

import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

/**
 * Formats information relevant to the "what_is_at" command into a String for logging.
 * @see IFormatter
 * @author John
 *
 */
public class WhatIsAtFormatter implements IFormatter {
	
	/**
	 * Implementation of IFormatter method.
	 * @see IFormatter
	 */
	@Override
	public String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\t" );
		sb.append( offset );
		sb.append( "\t\t" );
		sb.append( feature.getName() );
		sb.append( "  " );
		sb.append( feature.getCountyName() );
		sb.append( "  " );
		sb.append( feature.getAlphabeticStateCode() );
		
		return sb.toString();
	}
}
