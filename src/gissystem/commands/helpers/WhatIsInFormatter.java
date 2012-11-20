package gissystem.commands.helpers;

import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

public class WhatIsInFormatter implements IFormatter {
	@Override
	public String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\n\toffset:\t\t" );
		sb.append( offset );
		sb.append( "\n\tname:\t\t" );
		sb.append( feature.getName() );
		sb.append( "\n\tstate:\t\t" );
		sb.append( feature.getAlphabeticStateCode() );
		sb.append( "\n\tlatitude:\t" );
		sb.append( feature.getPrimaryLatitude().toString() );
		sb.append( "\n\tlongitude:\t" );
		sb.append( feature.getPrimaryLongitude().toString() );
		sb.append( "\n" );
		
		return sb.toString();
	}
}
