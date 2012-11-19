package gissystem.tests.commandtests;

import static org.junit.Assert.*;
import gissystem.commands.WorldCommand;
import gissystem.helpers.io.ConsoleLogger;
import gissystem.tests.helpers.DataAccessControllerStub;
import gissystem.tests.helpers.Point;

import org.junit.Before;
import org.junit.Test;

public class WorldCommandTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void executeTest() {
		WorldCommand worldCommand = new WorldCommand( -100, 100, -100, 100 );
		DataAccessControllerStub controller = new DataAccessControllerStub( new ConsoleLogger(), "world -100 100 -100 100" );
		worldCommand.execute( controller );
		
		assertEquals( "*", controller.getQuadTreeController().getQuadTreeToString() );
		assertFalse( controller.getQuadTreeController().insertToQuadTree( new Point( -389, -525 ), 100L ) );
	}

}
