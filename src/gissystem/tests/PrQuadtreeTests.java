package gissystem.tests;

import static org.junit.Assert.*;

import java.util.Vector;

import gissystem.datastructures.PrQuadtree;
import gissystem.tests.helpers.Point;

import org.junit.Before;
import org.junit.Test;

public class PrQuadtreeTests {
	private PrQuadtree tree;
	
	@Before
	public void setUp() throws Exception {
		tree = new PrQuadtree( -100, 100, -100, 100, 2 );
		tree.insert( new Point( 10, 10 ), 1L );
		tree.insert( new Point( -10, -10 ), 2L );
		tree.insert( new Point( -10, 10 ), 3L );
		tree.insert( new Point( 15, 15 ), 4L );
		
		tree.insert( new Point( 10, 10 ), 5L );
		tree.insert( new Point( 55, 60 ) , 6L );
		tree.insert( new Point( -25, -15 ), 7L );
		tree.insert( new Point( -17, -48 ), 8L );
		
		tree.insert( new Point( -17, -48 ), 9L );
		tree.insert( new Point( -18, -50 ), 10L );
	}

	@Test
	public void insertTest() {
		tree = new PrQuadtree( -100, 100, -100, 100, 2 );
		tree.insert( new Point( 10, 10 ), 1L );
		tree.insert( new Point( -10, -10 ), 2L );
		tree.insert( new Point( -10, 10 ), 3L );
		tree.insert( new Point( 15, 15 ), 4L );
		
		tree.insert( new Point( 10, 10 ), 5L );
		tree.insert( new Point( 55, 60 ) , 6L );
		
		//System.out.println( tree.toString() );
		fail("Not yet implemented");
	}

	@Test
	public void findTest() {
		Vector<Long> pointValues = new Vector<Long>();
		pointValues.add( 1L );
		pointValues.add( 5L );
		
		Vector<Long> regionValues = new Vector<Long>();
		regionValues.add( 8L );
		regionValues.add( 9L );
		regionValues.add( 10L );
		
		//System.out.println( tree.toString() );
		assertEquals( tree.find( new Point( 10, 10 ) ), pointValues );
		assertEquals( tree.find( -18, -17, -50, -48 ), regionValues );
	}
	
	@Test
	public void deleteTest() {
		System.out.println( tree.toString() );
		System.out.println( "\n\n" );
		tree.delete( new Point( 10, 10 ) );
		System.out.println( tree.toString() );
		System.out.println( "\n\n" );
		tree.delete( new Point( 15, 15 ) );
		System.out.println( tree.toString() );
		fail( "Not yet implemented" );
	}
}
