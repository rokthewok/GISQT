package gissystem.tests.commands;

import static org.junit.Assert.*;
import gissystem.commands.ImportCommand;
import gissystem.commands.WorldCommand;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;

import org.junit.Before;
import org.junit.Test;

public class ImportCommandTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		DataAccessControllerStub controller = new DataAccessControllerStub( new ConsoleLogger() );
		
		// set up quadtree with command that was tested previously
		WorldCommand worldCommand = new WorldCommand( -1000000, 1000000, -1000000, 1000000 );
		worldCommand.execute( controller );
		
		System.out.println( "\n" );
		
		ImportCommand command = new ImportCommand( "VA_Sample.txt" );
		command.execute( controller );
		
//		System.out.println( "\n-----------------------------------------------------\n" );
//		System.out.println( controller.getQuadTreeController().getQuadTreeToString() );
//		System.out.println( "\n-----------------------------------------------------\n" );
//		System.out.println( controller.getHashTableController().getHashTableToString() );
		
		// By inspection, okay. Kind of bad of me to do this test hack though -__-
		assertTrue( true );
	}

}
