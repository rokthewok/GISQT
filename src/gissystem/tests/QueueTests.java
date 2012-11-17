package gissystem.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gissystem.datastructures.Queue;

import org.junit.Before;
import org.junit.Test;

public class QueueTests {

	private Queue<Integer> queue;
	private Integer first;
	private List<Integer> list;
	
	@Before
	public void setUp() throws Exception {
		this.list = new ArrayList<Integer>( 25 );
		this.queue = new Queue<Integer>( 25 );
		Random generator = new Random();
		
		for( int i = 0; i < 25; i++ ) {
			int value = generator.nextInt();
			if( i == 0 ) {
				this.first = value;
			}
			queue.offer( value );
			list.add( value );
		}
	}

	@Test
	public void offerSuccessTest() {
		this.queue.poll();
		
		assertTrue( this.queue.offer( 10 ) );
	}
	
	@Test
	public void offerFailTest() {
		assertFalse( this.queue.offer( 10 ) );
	}

	@Test
	public void pollTest() {
		assertEquals( this.first, this.queue.poll() );
	}
	
	@Test
	public void insistTest() {
		assertEquals( this.first, this.queue.insist( new Integer( 10 ) ) );
	}
	
	@Test
	public void insistNullTest() {
		this.queue.poll();
		
		assertNull( this.queue.insist( new Integer( 10 ) ) );
	}
	
	@Test
	public void listsTest() {
		assertEquals( this.list, this.queue.toList() );
	}
}
