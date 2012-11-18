package gissystem.tests;

import static org.junit.Assert.*;
import gissystem.datastructures.BufferPool;

import org.junit.Before;
import org.junit.Test;

public class BufferPoolTests {
	BufferPool<Integer, String> pool;
	String defaultExpected;
	@Before
	public void setUp() throws Exception {
		pool = new BufferPool<Integer, String>( 10 );
		pool.add( 1, "This should be LRU" );
		pool.add( 17, "This should be fourth" );
		pool.add( 8, "This should be third" );
		pool.add( 58, "This should be second" );
		pool.add( 2, "This should be MRU" );
		
		defaultExpected = "MRU\n  2:\tThis should be MRU\n  58:\tThis should be second\n  8:\tThis should be third\n  17:\tThis should be fourth\n  1:\tThis should be LRU\nLRU";
	}

	@Test
	public void addTest() {
		//System.out.println( pool );
		assertEquals( defaultExpected, pool.toString() );
	}

	@Test
	public void getTest() {
		assertEquals( "This should be fourth", pool.get( 17 ) );
		assertEquals( "This should be MRU", pool.get( 2 ) );
		assertEquals( "This should be LRU", pool.get( 1 ) );
	}
	
	@Test
	public void pushesOutLRUTest() {
		// pool has max size 10; already has 5 items in it
		pool.add( 27, "pushing..." );
		pool.add( 78, "shoving..." );
		pool.add( 106, "bulling..." );
		pool.add( 10, "almost there..." );
		pool.add( 81, "one more..." );
		pool.add( 53, "Ah, this is our new MRU that pushed stuff out" );
		
		//System.out.println( pool );
		assertNull( pool.get( 1 ) );
	}
	
	@Test
	public void updatesMRUTest() {
		assertEquals( defaultExpected, pool.toString() );
		pool.get( 1 );
		String newExpected = "MRU\n  1:\tThis should be LRU\n  2:\tThis should be MRU\n  58:\tThis should be second\n  8:\tThis should be third\n  17:\tThis should be fourth\nLRU";
		assertEquals( newExpected, pool.toString() );
	}
}
