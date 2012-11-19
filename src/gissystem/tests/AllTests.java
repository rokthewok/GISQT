package gissystem.tests;

import gissystem.tests.commandtests.CommandTests;
import gissystem.tests.controllertests.ControllerTests;
import gissystem.tests.datastructuretests.DataStructuresTests;
import gissystem.tests.factorytests.FactoriesTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ControllerTests.class, DataStructuresTests.class, FactoriesTests.class, CommandTests.class })
public class AllTests {

}
