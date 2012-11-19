package gissystem.commands;

import java.util.List;

import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.models.GeographicFeature;

public class WhatIsCommand implements ICommand {
	private String featureName;
	private String stateAbbreviation;
	
	public WhatIsCommand( String featureName, String stateAbbreviation ) {
		this.featureName = featureName;
		this.stateAbbreviation = stateAbbreviation;
	}
	
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getHashTableController().findFeature( this.featureName, this.stateAbbreviation );
		
		controller.getLogger().writeToLog( "The features found with name \"" + this.featureName + "\" and state \"" + this.stateAbbreviation +"\" are:\n" );
		if( offsets.isEmpty() ) {
			controller.getLogger().writeToLog( "\tno results." );
		} else {
			// for each offset found in the hashtable, log the offset, county, latitude, and longitude of the feature
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( formatFeatureOutput( offset, feature ) );
			}
		}
	}
	
	protected String formatFeatureOutput( Long offset, GeographicFeature feature ) {
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
