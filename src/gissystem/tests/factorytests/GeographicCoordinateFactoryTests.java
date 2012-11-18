package gissystem.tests.factorytests;

import static org.junit.Assert.*;
import gissystem.factories.GeographicCoordinateFactory;
import gissystem.models.GeographicCoordinate;

import org.junit.Before;
import org.junit.Test;

public class GeographicCoordinateFactoryTests {
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void shouldCreateLongitudeCoordinateTest() {
		GeographicCoordinate coordinate = GeographicCoordinateFactory.createCoordinate( "0692310W", "0.01" );
		
		assertTrue( coordinate.getDecimalValue() == 0.01 );
		assertEquals( "West", coordinate.getCardinalDirection() );
		assertEquals( 69, coordinate.getDegrees() );
		assertEquals( 23, coordinate.getMinutes() );
		assertEquals( 10, coordinate.getSeconds() );
		assertEquals( -( 69 * 3600 + 23 * 60 + 10 ), coordinate.toSeconds() );
	}
	
	@Test
	public void shouldCreateLatitudeCoordinateTest() {
		GeographicCoordinate coordinate = GeographicCoordinateFactory.createCoordinate( "724805N", "0.5" );
		
		assertTrue( coordinate.getDecimalValue() == 0.5 );
		assertEquals( "North", coordinate.getCardinalDirection() );
		assertEquals( 72, coordinate.getDegrees() );
		assertEquals( 48, coordinate.getMinutes() );
		assertEquals( 5, coordinate.getSeconds() );
		assertEquals( 72 * 3600 + 48 * 60 + 5, coordinate.toSeconds() );
	}
}
