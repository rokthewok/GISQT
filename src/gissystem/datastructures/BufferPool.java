package gissystem.datastructures;

import java.util.Vector;

/**
 * A caching system using LRU replacement.
 * @author John "Riff-Raff" Ruffer
 *
 * @param <K> The key type used to identify the item in the cache.
 * @param <V> The value type of the items being stored.
 */
public class BufferPool<K, V> {
	private int size;
	private Vector<Pair> pool;
	
	/**
	 * Default ctor. Sets pool size to 20.
	 */
	public BufferPool() {
		this( 20 );
	}
	
	/**
	 * ctor.
	 * @param size The size of the pool.
	 */
	public BufferPool( int size ) {
		this.size = size;
		this.pool = new Vector<Pair>( size );
	}
	
	/**
	 * Tries to get a value from the pool.
	 * @param key The key corresponding to the desired value.
	 * @return The desired value or null if the item is not found.
	 */
	public V get( K key ) {
		for( Pair pair : this.pool ) {
			if( key.equals( pair.key ) ) {
				Pair temp = pair;
				// move the item accessed to the MRU position (front of the vector)
				this.pool.remove( pair );
				this.pool.add( 0, temp );
				
				return temp.value;
			}
		}
		
		// not found in pool
		return null;
	}
	
	/**
	 * Inserts an item at the MRU position of the pool.
	 * @param key The key corresponding to the value.
	 * @param value The item to be inserted.
	 */
	public void add( K key, V value ) {
		if( this.pool.size() == this.size ) {
			// remove LRU item
			this.pool.remove( this.size - 1 );
		}
		
		// insert at front (MRU) end of the pool
		this.pool.add( 0, new Pair( key, value ) );
	}
	
	/**
	 * String representation of the bufferpool, in MRU -> LRU format.
	 * @return The String representation of the pool.
	 */
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
	
	/**
	 * Internal class used by the bufferpool to store key-value pairs.
	 * @author John Ruffer
	 *
	 */
	private class Pair {
		private K key;
		private V value;
		
		public Pair( K key, V value ) {
			this.key = key;
			this.value = value;
		}
	}
}
