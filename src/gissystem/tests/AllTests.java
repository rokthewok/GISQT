package gissystem.tests;

import gissystem.tests.controllertests.DatabaseControllerTests;
import gissystem.tests.controllertests.QuadTreeControllerTests;
import gissystem.tests.datastructuretests.BufferPoolTests;
import gissystem.tests.datastructuretests.HashTableTests;
import gissystem.tests.datastructuretests.PrQuadtreeTests;
import gissystem.tests.factorytests.GeographicCoordinateFactoryTests;
import gissystem.tests.factorytests.GeographicFeatureFactoryTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatabaseControllerTests.class, QuadTreeControllerTests.class, BufferPoolTests.class, HashTableTests.class, PrQuadtreeTests.class,
	GeographicCoordinateFactoryTests.class, GeographicFeatureFactoryTests.class })
public class AllTests {

}
