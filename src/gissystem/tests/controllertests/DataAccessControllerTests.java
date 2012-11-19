package gissystem.tests.controllertests;

import static org.junit.Assert.*;

import gissystem.controllers.DataAccessController;
import gissystem.helpers.io.ConsoleLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;

public class DataAccessControllerTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getNextCommandLineTest() throws IOException {
		File file = new File( "command.txt" );
		file.delete();
		
		String firstLine = "this is the first line to get";
		String secondLine = "this is the second line to get";
		RandomAccessFile commands = new RandomAccessFile( file, "rw" );
		commands.writeBytes( firstLine );
		commands.writeBytes( "\n" );
		commands.writeBytes( secondLine );
		commands.writeBytes( "\n" );
		commands.seek( 0 );
		
		DataAccessController controller = new DataAccessController( new File( "database.txt" ), commands, new ConsoleLogger() );
		
		assertEquals( firstLine, controller.getNextCommandLine() );
		assertEquals( secondLine, controller.getNextCommandLine() );
		assertNull( controller.getNextCommandLine() );
	}

}
