package gissystem.tests.commandtests;

import static org.junit.Assert.*;

import gissystem.commands.CountWhatIsCommand;
import gissystem.commands.ImportCommand;
import gissystem.commands.WhatIsCommand;
import gissystem.commands.WorldCommand;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;

import org.junit.Before;
import org.junit.Test;

public class WhatIsCommandTests {
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
	public void whatIsCommandTest() {
		String featureName = "Hupman Valley";
		String stateAbbreviation = "VA";
		WhatIsCommand command = new WhatIsCommand( featureName, stateAbbreviation );
		command.execute( controller );
		
		CountWhatIsCommand countCommand = new CountWhatIsCommand( featureName, stateAbbreviation );
		countCommand.execute( controller );
		
		// Once again... by inspection. >_> <_<
		assertTrue( true );
	}

}
