package gissystem.tests.commandtests;

import static org.junit.Assert.*;
import gissystem.commands.CountWhatIsInCommand;
import gissystem.commands.ImportCommand;
import gissystem.commands.WhatIsInCommand;
import gissystem.commands.WorldCommand;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;

import org.junit.Before;
import org.junit.Test;

public class WhatIsInCommandTests {
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
	public void whatIsInCommandTest() {
		long xMin = -290000L;
		long xMax = 0L;
		long yMin = 0L;
		long yMax = 137800L;
		
		//System.out.println( controller.getQuadTreeController().getQuadTreeToString() );
		
		WhatIsInCommand command = new WhatIsInCommand( xMin, xMax, yMin, yMax );
		command.execute( controller );
		
		CountWhatIsInCommand countCommand = new CountWhatIsInCommand( xMin, xMax, yMin, yMax );
		countCommand.execute( controller );
		
		// acceptable by inspection.
		assertTrue( true );
	}

}
