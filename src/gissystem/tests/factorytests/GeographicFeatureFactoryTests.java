package gissystem.tests.factorytests;

import static org.junit.Assert.*;
import gissystem.factories.GeographicCoordinateFactory;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.models.GeographicFeature;

import org.junit.Before;
import org.junit.Test;

public class GeographicFeatureFactoryTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldCreateFeatureTest() {
		String record = "1481345|Asbury Church|Church|VA|51|Highland|091|382607N|0793312W|38.4353981|-79.5533807|||||818|2684|Monterey|09/28/1979|";
		GeographicFeature actual = GeographicFeatureFactory.createGeographicFeature( record );
		GeographicFeature expected = new GeographicFeature();
		expected.setId( 1481345 );
		expected.setName( "Asbury Church" );
		expected.setClassification( "Church" );
		expected.setAlphabeticStateCode( "VA" );
		expected.setNumericStateCode( 51 );
		expected.setCountyName( "Highland" );
		expected.setNumericCountyCode( 91 );
		expected.setPrimaryLatitude( GeographicCoordinateFactory.createCoordinate( "382607N", "38.4353981" ) );
		expected.setPrimaryLongitude( GeographicCoordinateFactory.createCoordinate( "0793312W", "-79.5533807" ) );
		expected.setElevationInMeters( 818 );
		expected.setElevationInFeet( 2684 );
		expected.setMapName( "Monterey" );
		expected.setDateCreated( "09/28/1979" );
		
		expected.equals( actual );
		assertTrue( expected.equals( actual ) );
		assertEquals( expected, actual );
	}

}
