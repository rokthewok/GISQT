package gissystem.tests.controllertests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import gissystem.controllers.HashTableController;

import org.junit.Before;
import org.junit.Test;

public class HashTableControllerTests {
	@Test
	public void addFeatureTest() {
		HashTableController controller = new HashTableController();
		assertTrue( controller.addFeature( "Falling Water", "PA", 253L ) != -1 );
		assertTrue( controller.addFeature( "Guggenheim", "NY", 827L ) != -1 );
		// make sure there is no double insertion
		assertTrue( controller.addFeature( "Falling Water", "PA", 253L ) == -1 );
		//System.out.println( controller.getHashTableToString() );
	}
	
	@Test
	public void findFeatureTest() {
		HashTableController controller = new HashTableController();
		controller.addFeature( "Falling Water", "PA", 253L );
		controller.addFeature( "Guggenheim", "NY", 827L );
		controller.addFeature( "Falling Water", "PA", 776L );
		
		List<Long> expectedResults = new ArrayList<Long>();
		expectedResults.add( 253L );
		expectedResults.add( 776L );
		assertEquals( expectedResults, controller.findFeature( "Falling Water", "PA" ) );
		
		expectedResults.clear();
		expectedResults.add( 827L );
		assertEquals( expectedResults, controller.findFeature( "Guggenheim", "NY" ) );
	}
}
