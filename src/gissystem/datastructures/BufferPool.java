package gissystem.datastructures;

import java.util.Vector;

public class BufferPool<K, V> {
	private int size;
	private Vector<Pair> pool;
	
	public BufferPool() {
		this( 20 );
	}
	
	public BufferPool( int size ) {
		this.size = size;
		this.pool = new Vector<Pair>( size );
	}
	
	public V get( K key ) {
		for( Pair pair : this.pool ) {
			if( key.equals( pair.key ) ) {
				Pair temp = pair;
				this.pool.remove( pair );
				this.pool.add( 0, temp );
				
				return temp.value;
			}
		}
		
		// not found in pool
		return null;
	}
	
	public void add( K key, V value ) {
		if( this.pool.size() == this.size ) {
			// remove LRU item
			this.pool.remove( this.size - 1 );
		}
		
		// insert at front (MRU) end of the pool
		this.pool.add( 0, new Pair( key, value ) );
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "MRU\n" );
		for( Pair pair : this.pool ) {
			sb.append( "  " );
			sb.append( pair.key );
			sb.append( ":\t" );
			sb.append( pair.value );
			sb.append( "\n" );
		}
		sb.append( "LRU" );
		
		return sb.toString();
	}
	
	private class Pair {
		private K key;
		private V value;
		
		public Pair( K key, V value ) {
			this.key = key;
			this.value = value;
		}
	}
}
