package gissystem.tests.controllertests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatabaseControllerTests.class, QuadTreeControllerTests.class })
public class ControllerTests {

}
