package gissystem.tests;

import static org.junit.Assert.*;
import gissystem.datastructures.HashTable;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class HashTableTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void insertTest() {
		Integer [] array = new Integer[700];
		HashTable<Integer> table = new HashTable<Integer>();
		Random generator = new Random();
		
		for( int i = 0; i < 700; i++ ) {
			Integer value = generator.nextInt( 1000 );
			array[i] = value;
			String key = "hey! " + value.toString();
			table.insert( key, value );
		}

		assertEquals( table.size(), array.length );
	}

	@Test
	public void removeTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		assertEquals( new Integer( 25 ), table.remove( "hello there" ) );
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
		
		assertEquals( new Integer( 25 ), table.get( "hello there" ) );
	}
	
	@Test
	public void getFailTest() {
		HashTable<Integer> table = new HashTable<Integer>();
		table.insert( "hello there", new Integer( 25 ) );
		
		assertNull( table.get( "well hi!" ) );
	}
}
