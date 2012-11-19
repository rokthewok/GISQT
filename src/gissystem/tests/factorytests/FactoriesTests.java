package gissystem.tests.factorytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GeographicCoordinateFactoryTests.class, GeographicFeatureFactoryTests.class, CommandFactoryTests.class })
public class FactoriesTests {

}
