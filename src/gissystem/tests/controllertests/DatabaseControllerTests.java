package gissystem.tests.controllertests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import gissystem.controllers.DatabaseController;

import org.junit.Before;
import org.junit.Test;

public class DatabaseControllerTests {
	DatabaseController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = null;
		try {
			File db = new File( "database.txt" );
			db.delete();
			controller = new DatabaseController();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.add( "this is a feature" );
		controller.add( "this is also a feature" );
		controller.add( "this is not a feature" );
	}

	@Test
	public void addTest() {
		if( controller == null ) {
			fail( "Controller creation failed." );
		}
		
		String expectedBufferToString = "MRU\n  41:\tthis is not a feature\n  18:\tthis is also a feature\n  0:\tthis is a feature\nLRU";
		
		assertEquals( expectedBufferToString, controller.bufferPoolToString() );
	}

	@Test
	public void getHitsBufferTest() {
		if( controller == null ) {
			fail( "Controller creation failed." );
		}
		
		
	}
	
	@Test
	public void getHitsDatabaseTest() {
		if( controller == null ) {
			fail( "Controller creation failed." );
		}
		
		controller.add( "fab" );
		controller.add( "bab" );
		controller.add( "cab" );
		controller.add( "lad" );
		controller.add( "bad" );
		controller.add( "nab" );
		controller.add( "tab" );
		controller.add( "tad" );
		controller.add( "sad" );
		controller.add( "fad" );
		controller.add( "pad" );
		controller.add( "gab" );
		controller.add( "add" );
		controller.add( "had" );
		controller.add( "rad" );
		controller.add( "kad" );
		controller.add( "kab" );
		controller.add( "wad" );
		
		String expectedBuffer = controller.bufferPoolToString();
		
		String expectedResult = "this is a feature";
		String actualResult = controller.get( 0L );
		
		String actualBuffer = controller.bufferPoolToString();
		
		assertEquals( expectedBuffer, actualBuffer );
		assertEquals( expectedResult, actualResult );
		
	}
}
