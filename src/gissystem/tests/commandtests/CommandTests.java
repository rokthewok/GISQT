package gissystem.tests.commandtests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ImportCommandTests.class, WorldCommandTests.class, WhatIsAtCommandTests.class, WhatIsCommandTests.class, WhatIsInCommandTests.class })
public class CommandTests {

}
