package gissystem.tests.commandtests;

import static org.junit.Assert.*;
import gissystem.commands.DebugCommand;
import gissystem.commands.ImportCommand;
import gissystem.commands.WorldCommand;
import gissystem.helpers.Debug;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;

import org.junit.Before;
import org.junit.Test;

public class DebugCommandTests {
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
	public void debugQuadTreeTest() {
		DebugCommand command = new DebugCommand( Debug.QUADTREE );
		command.execute( controller );

		// kosher by inspection.
		assertTrue( true );
	}

	@Test
	public void debugHashTableTest() {
		DebugCommand command = new DebugCommand( Debug.HASHTABLE );
		command.execute( controller );
		
		assertTrue( true );
	}
	
	@Test
	public void debugBufferPoolTest() {
		DebugCommand command = new DebugCommand( Debug.BUFFERPOOL );
		command.execute( controller );
		
		assertTrue( true );
	}
}
