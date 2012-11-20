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
		sb.append( "\toffset: " );
		sb.append( offset );
		sb.append( "\n\tfeature name: " );
		sb.append( feature.getName() );
		sb.append( "\n\tfeature county: " );
		sb.append( feature.getCountyName() );
		sb.append( "\n\tfeature state: " );
		sb.append( feature.getAlphabeticStateCode() );
		sb.append( "\n" );
		
		return sb.toString();
	}
}
