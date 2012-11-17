package gissystem.factories;

import java.beans.IntrospectionException;
import gissystem.models.GeographicFeature;
import gissystem.models.helpers.BeanFacade;
import gissystem.exceptions.SetPropertyException;

/**
 * Factory for {@link GeographicFeature}.
 * @author John Ruffer
 *
 */
public class GeographicFeatureFactory {
	// TODO Make into static ints?
	private enum FeatureProperties {
		ID, NAME, CLASSIFICATION, ALPHABETIC_STATE_CODE,
		NUMERIC_STATE_CODE, COUNTY_NAME, NUMERIC_COUNTY_CODE,
		PRIMARY_LATITUDE, PRIMARY_LONGITUDE, PRIMARY_LATITUDE_DEC,
		PRIMARY_LONGITUDE_DEC, SOURCE_LATITUDE, SOURCE_LONGITUDE,
		SOURCE_LATITUDE_DEC, SOURCE_LONGITUDE_DEC, ELEVATION_IN_METERS,
		ELEVATION_IN_FEET, MAP_NAME, DATE_CREATED, DATE_EDITED
	}
	
	/**
	 * Creates a GeographicFeature from a record.
	 * @param record The String representation of a GIS record.
	 * @return
	 */
	public static GeographicFeature createGeographicFeature( String record ) {
		String [] featureProperties = record.split( "\\|" );
		
		GeographicFeature feature = new GeographicFeature();
		
		BeanFacade<GeographicFeature> featureBean = null;
		try {
			featureBean = new BeanFacade<GeographicFeature>( feature );
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		setFeatureProperties( featureBean, featureProperties );
		
		return feature;
	}
	
	/**
	 * Sets the properties of the GeographicFeature using reflection.
	 * @param feature The feature to which to assign properties.
	 * @param values The corresponding values to assign.
	 */
	private static void setFeatureProperties( BeanFacade<GeographicFeature> feature, String [] values ) {	
		setFeatureProperty( feature, "id", values[FeatureProperties.ID.ordinal()] );
		setFeatureProperty( feature, "name", values[FeatureProperties.NAME.ordinal()] );
		setFeatureProperty( feature, "classification", values[FeatureProperties.CLASSIFICATION.ordinal()] );
		setFeatureProperty( feature, "alphabeticStateCode", values[FeatureProperties.ALPHABETIC_STATE_CODE.ordinal()] );
		setFeatureProperty( feature, "numericStateCode", values[FeatureProperties.NUMERIC_STATE_CODE.ordinal()] );
		setFeatureProperty( feature, "countyName", values[FeatureProperties.COUNTY_NAME.ordinal()] );
		setFeatureProperty( feature, "numericCountyCode", values[FeatureProperties.NUMERIC_COUNTY_CODE.ordinal()] );
		setFeatureProperty( feature, "elevationInMeters", values[FeatureProperties.ELEVATION_IN_METERS.ordinal()] );
		setFeatureProperty( feature, "elevationInFeet", values[FeatureProperties.ELEVATION_IN_FEET.ordinal()] );
		setFeatureProperty( feature, "mapName", values[FeatureProperties.MAP_NAME.ordinal()] );
		setFeatureProperty( feature, "dateCreated", values[FeatureProperties.DATE_CREATED.ordinal()] );
		
		if( values.length > FeatureProperties.DATE_EDITED.ordinal() ) {
			setFeatureProperty( feature, "dateEdited", values[FeatureProperties.DATE_EDITED.ordinal()] );
		}
		
		feature.getBeanObject().setPrimaryLatitude(
				GeographicCoordinateFactory.createCoordinate(
						values[FeatureProperties.PRIMARY_LATITUDE.ordinal()],
						values[FeatureProperties.PRIMARY_LATITUDE_DEC.ordinal()] ) );
		
		feature.getBeanObject().setPrimaryLongitude(
				GeographicCoordinateFactory.createCoordinate(
						values[FeatureProperties.PRIMARY_LONGITUDE.ordinal()],
						values[FeatureProperties.PRIMARY_LONGITUDE_DEC.ordinal()] ) );
		
		feature.getBeanObject().setSourceLatitude(
				GeographicCoordinateFactory.createCoordinate(
						values[FeatureProperties.SOURCE_LATITUDE.ordinal()],
						values[FeatureProperties.SOURCE_LATITUDE_DEC.ordinal()] ) );
		
		feature.getBeanObject().setSourceLongitude(
				GeographicCoordinateFactory.createCoordinate(
						values[FeatureProperties.SOURCE_LONGITUDE.ordinal()],
						values[FeatureProperties.SOURCE_LONGITUDE_DEC.ordinal()] ) );
	}
	
	/**
	 * Sets a property of the GeographicFeature.
	 * @param feature The feature.
	 * @param name The programmatic name of the property.
	 * @param value The value to assign to the property.
	 */
	private static void setFeatureProperty( BeanFacade<GeographicFeature> feature, 
			String name, String value ) {
		if( canParseInt( value ) ) {
			setIntegerFeatureProperty( feature, name, value );
		} else {
			setStringFeatureProperty( feature, name, value );
		}
	}
	
	/**
	 * Checks if integer parsing is possible.
	 * @param value The String representation of a (possible) integer
	 * @return
	 */
	private static boolean canParseInt( String value ) {
		try {
			Integer.parseInt( value );
		} catch( NumberFormatException e ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets a property which takes an Integer.
	 * @param feature
	 * @param name
	 * @param value
	 */
	private static void setIntegerFeatureProperty( BeanFacade<GeographicFeature> feature, 
			String name, String value ) {
		Integer intValue = null;
		try {
			intValue = Integer.parseInt( value );
		} catch( NumberFormatException e ) {
			e.printStackTrace();
		}
		
		try {
			feature.setProperty( name, intValue );
		} catch (SetPropertyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets a property which takes a String.
	 * @param feature
	 * @param name
	 * @param value
	 */
	private static void setStringFeatureProperty( BeanFacade<GeographicFeature> feature, 
			String name, String value ) {
		try {
			feature.setProperty( name, value );
		} catch( SetPropertyException e ) {
			e.printStackTrace();
		}
	}
}

