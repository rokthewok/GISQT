package gissystem.tests.factorytests;

import static org.junit.Assert.*;
import gissystem.commands.CountWhatIsAtCommand;
import gissystem.commands.CountWhatIsCommand;
import gissystem.commands.CountWhatIsInCommand;
import gissystem.commands.DebugCommand;
import gissystem.commands.ImportCommand;
import gissystem.commands.WhatIsAtCommand;
import gissystem.commands.WhatIsCommand;
import gissystem.commands.WhatIsInCommand;
import gissystem.commands.WorldCommand;
import gissystem.factories.DebugCommandFactory;
import gissystem.factories.ImportCommandFactory;
import gissystem.factories.WhatIsAtCommandFactory;
import gissystem.factories.WhatIsCommandFactory;
import gissystem.factories.WhatIsInCommandFactory;
import gissystem.factories.WorldCommandFactory;
import gissystem.interfaces.ICommandFactory;

import org.junit.Before;
import org.junit.Test;

public class CommandFactoryTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void worldCommandFactorytest() {
		String command = "world\t0872214W\t0782808W\t225300N\t385763N";

		ICommandFactory factory = new WorldCommandFactory();
		assertTrue( factory.build( command ) instanceof WorldCommand );
	}
	
	@Test
	public void importCommandFactoryTest() {
		String command = "import\tVA_Sample.txt";
		
		ICommandFactory factory = new ImportCommandFactory();
		assertTrue( factory.build( command ) instanceof ImportCommand );
	}
	
	@Test
	public void whatIsCommandFactoryTest() {
		String command = "what_is\tAsbury Church\tVA";
		String longCommand = "what_is\t-l\tAsbury Church\tVA";
		String countCommand = "what_is\t-c\tAsbury Church\tVA";
		
		ICommandFactory factory = new WhatIsCommandFactory();
		
		assertTrue( factory.build( command ) instanceof WhatIsCommand );
		assertTrue( factory.build( longCommand ) instanceof WhatIsCommand );
		assertTrue( factory.build( countCommand ) instanceof CountWhatIsCommand );
	}
	
	@Test
	public void whatIsAtCommandFactoryTest() {
		String command = "what_is_at\t382710N\t0794207W";
		String longCommand = "what_is_at\t-l\t382710N\t0794207W";
		String countCommand = "what_is_at\t-c\t382710N\t0794207W";
		
		ICommandFactory factory = new WhatIsAtCommandFactory();
		
		assertTrue( factory.build( command ) instanceof WhatIsAtCommand );
		assertTrue( factory.build( longCommand ) instanceof WhatIsAtCommand );
		assertTrue( factory.build( countCommand ) instanceof CountWhatIsAtCommand );
	}
	
	@Test
	public void whatIsInCommandFactoryTest() {
		String command = "what_is_in\t382710N 0794207W\t10000\t10000";
		String longCommand = "what_is_in\t-l\t382710N 0794207W\t10000\t10000";
		String countCommand = "what_is_in\t-c\t382710N 0794207W\t10000\t10000";
		
		ICommandFactory factory = new WhatIsInCommandFactory();
		
		assertTrue( factory.build( command ) instanceof WhatIsInCommand );
		assertTrue( factory.build( longCommand ) instanceof WhatIsInCommand );
		assertTrue( factory.build( countCommand ) instanceof CountWhatIsInCommand );
	}
	
	@Test
	public void debugCommandFactoryTest() {
		String quadCommand = "debug\tquad";
		String hashCommand = "debug\thash";
		String poolCommand = "debug\tpool";
		
		ICommandFactory factory = new DebugCommandFactory();
		
		assertTrue( factory.build( quadCommand ) instanceof DebugCommand );
		assertTrue( factory.build( hashCommand ) instanceof DebugCommand );
		assertTrue( factory.build( poolCommand ) instanceof DebugCommand );
	}
}
