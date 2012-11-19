package gissystem.tests.controllertests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatabaseControllerTests.class, QuadTreeControllerTests.class, DataAccessControllerTests.class, HashTableControllerTests.class })
public class ControllerTests {

}
