package gissystem.factories;

import gissystem.models.GeographicFeature;

/**
 * Factory for {@link GeographicFeature}.
 * @author John Ruffer
 *
 */
public class GeographicFeatureFactory {
	
	/**
	 * Creates a GeographicFeature from a record.
	 * @param record The String representation of a GIS record.
	 * @return
	 */
	public static GeographicFeature createGeographicFeature( String record ) {
		GeographicFeature feature = new GeographicFeature();
		setFeatureProperties( feature, record );
		
		return feature;
	}
	
	private static void setFeatureProperties( GeographicFeature feature, String record ) {
		String [] parts = record.split( "\\|" );
		
		if( !parts[0].isEmpty() ) {
			feature.setId( new Integer( parts[0] ) );
		}
		
		if( !parts[1].isEmpty() ) {
			feature.setName( parts[1] );
		}
		
		if( !parts[2].isEmpty() ) {
			feature.setClassification( parts[2] );
		}
		
		if( !parts[3].isEmpty() ) {
			feature.setAlphabeticStateCode( parts[3] );
		}
		
		if( !parts[4].isEmpty() ) {
			feature.setNumericStateCode( new Integer( parts[4] ) );
		}
		
		if( !parts[5].isEmpty() ) {
			feature.setCountyName( parts[5] );
		}
		
		if( !parts[6].isEmpty() ) {
			feature.setNumericCountyCode( new Integer( parts[6] ) );
		}
		
		if( !parts[7].isEmpty() ) {
			feature.setPrimaryLatitude( GeographicCoordinateFactory.createCoordinate( parts[7], parts[9] ) );
		}
		
		if( !parts[8].isEmpty() ) {
			feature.setPrimaryLongitude( GeographicCoordinateFactory.createCoordinate( parts[8], parts[10] ) );
		}
		
		if( !parts[11].isEmpty() ) {
			feature.setSourceLatitude( GeographicCoordinateFactory.createCoordinate( parts[11], parts[13] ) );
		}
		
		if( !parts[12].isEmpty() ) {
			feature.setSourceLongitude( GeographicCoordinateFactory.createCoordinate( parts[12], parts[14] ) );
		}
		
		if( !parts[15].isEmpty() ) {
			feature.setElevationInMeters( new Integer( parts[15] ) );
		}
		
		if( !parts[16].isEmpty() ) {
			feature.setElevationInFeet( new Integer( parts[16] ) );
		}
		
		if( !parts[17].isEmpty() ) {
			feature.setMapName( parts[17] );
		}
		
		if( !parts[18].isEmpty() ) {
			feature.setDateCreated( parts[18] );
		}
		
		if( parts.length == 20 && !parts[19].isEmpty() ) {
			feature.setDateEdited( parts[19] );
		}
	}
}

