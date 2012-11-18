package gissystem.tests;

import static org.junit.Assert.*;
import gissystem.datastructures.HashTable;

import java.util.Random;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class HashTableTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void insertTest() {
		Vector<Integer> array = new Vector<Integer>(700);
		HashTable<Integer> table = new HashTable<Integer>();
		Random generator = new Random();
		
		for( int i = 0; i < 700; i++ ) {
			Integer value = generator.nextInt( 1000 );
			if( array.contains( value ) ) {
				continue;
			}
			array.add( value );
			String key = "hey! " + value.toString();
			table.insert( key, value );
		}

		assertEquals( array.size(), table.size() );
	}
	
	@Test
	public void insertProbingTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		assertEquals( 0, table.insert( "hey!", new Integer( 25 ) ) );
		// dunno how to choose a key that will hash to the same value...
		//assertEquals( 1, table.insert( "hey!", new Integer( 15 ) ) );
		assertEquals( -1, table.insert( "hey!", new Integer( 25 ) ) );
	}

	@Test
	public void removeTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		Vector<Integer> values = new Vector<Integer>();
		values.add( new Integer( 25 ) );
		assertEquals( values, table.remove( "hello there" ) );
	}
	
	@Test
	public void removeEmptyTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		table.remove( "hello there" );
		assertEquals( 0, table.size() );
	}
	
	@Test
	public void getTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		Vector<Integer> values = new Vector<Integer>();
		values.add( new Integer( 25 ) );
		assertEquals( values, table.get( "hello there" ) );
		
		table.insert( "hello there", new Integer( 30 ) );
		values.add( new Integer( 30 ) );
		assertEquals( values, table.get( "hello there" ) );
	}
	
	@Test
	public void getFailTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		assertNull( table.get( "well hi!" ) );
	}
}
