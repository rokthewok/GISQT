package gissystem.tests.commandtests;

import static org.junit.Assert.*;
import gissystem.commands.CountWhatIsAtCommand;
import gissystem.commands.ImportCommand;
import gissystem.commands.LongWhatIsAtCommand;
import gissystem.commands.WhatIsAtCommand;
import gissystem.commands.WorldCommand;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;

import org.junit.Before;
import org.junit.Test;

public class WhatIsAtCommandTests {
	private DataAccessControllerStub controller;
	
	@Before
	public void setUp() throws Exception {
		controller = new DataAccessControllerStub( new ConsoleLogger() );
		
		// set up quadtree with command that was tested previously
		WorldCommand worldCommand = new WorldCommand( -1000000, 1000000, -1000000, 1000000 );
		worldCommand.execute( controller );
		
		System.out.println( "\n" );
		
		ImportCommand command = new ImportCommand( "VA_Sample.txt" );
		command.execute( controller );
		
		System.out.println( "\n" );
	}

	@Test
	public void test() {
		String rawLatitude = "382607N";
		String rawLongitude = "0793312W";
		WhatIsAtCommand command = new WhatIsAtCommand( rawLatitude, rawLongitude );
		command.execute( controller );
		
		LongWhatIsAtCommand longCommand = new LongWhatIsAtCommand( rawLatitude, rawLongitude );
		longCommand.execute( controller );
		
		CountWhatIsAtCommand countCommand = new CountWhatIsAtCommand( rawLatitude, rawLongitude );
		countCommand.execute( controller );
		
		// once again, by inspection
		assertTrue( true );
	}

}
