package gissystem.commands.helpers;

import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

public class WhatIsFormatter implements IFormatter {
	@Override
	public String formatFeatureOutput( Long offset, GeographicFeature feature ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\toffset:\t\t" );
		sb.append( offset );
		sb.append( "\n\tcounty:\t\t" );
		sb.append( feature.getCountyName() );
		sb.append( "\n\tlatitude:\t" );
		sb.append( feature.getPrimaryLatitude().toString() );
		sb.append( "\n\tlongitude:\t" );
		sb.append( feature.getPrimaryLongitude().toString() );
		sb.append( "\n" );
		
		return sb.toString();
	}
}
