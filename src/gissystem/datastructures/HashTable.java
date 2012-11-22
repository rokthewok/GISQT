package gissystem.datastructures;

import java.util.Vector;

public class HashTable<T> {
	private Object [] elements;
	private int tableSize;
	private int currentPopulation;
	private static int [] TABLE_SIZES = {
											1019, 2027, 4079, 16267,
											32503, 65011, 130027, 260111, 
											520279, 1040387, 2080763, 
											4161539, 8323151, 16646323
										};
	
	public HashTable() {
		this.tableSize = TABLE_SIZES[0];
		this.currentPopulation = 0;
		this.elements = new Object[this.tableSize];
	}
	
	/**
	 * Inserts an item in the table. Returns the number of probe steps it took to insert the value, or -1 if the item is already in the table.
	 * @param key
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int insert( String key, T element ) {
		int hashValue = elfHash( key ) % this.tableSize;
		
		boolean inserted = false;
		int i = 1;
		int index = hashValue;
		
		while( this.elements[index] != null ) {
			// if the keys are equal, then add the value to the list of values (one-to-many relationship)
			if( ( (Pair<T>) this.elements[index] ).key.equals( key ) ) {
				// check if the item is already in the table
				if( ( (Pair<T>) this.elements[index] ).values.contains( element ) ) {
					return -1;
				}
				
				( (Pair<T>) this.elements[index] ).values.add( element );
				
				inserted = true;
				break;
			}
			// get the next probing value
			index = nextProbe( i, hashValue );
			i++;
		}
		
		// if inserted is false, then we need to make a new entry
		if( !inserted ) {
			Pair<T> pair = new Pair<T>( key, element );
			this.elements[index] = pair;
		
			// since we added a new key entry, then we need to increment the population of the table 
			this.currentPopulation++;
			if( this.currentPopulation >= ( this.tableSize * 0.70 ) 
					&& this.tableSize != TABLE_SIZES[TABLE_SIZES.length - 1] ) {
				this.grow();
			}
		}
		
		// return the number of probes needed to insert
		return i - 1;
	}
	
	/**
	 * Remove item(s) associated with the given key from the table.
	 * @param key The key to be located.
	 * @return A Vector of items that were removed
	 */
	@SuppressWarnings("unchecked")
	public Vector<T> remove( String key ) {
		int hashValue = elfHash( key ) % this.tableSize;
		
		Pair<T> temp = null;
		int i = 1;
		int index = hashValue;
		
		while( this.elements[index] != null ) {
			temp = (Pair<T>) this.elements[index];
			if( temp.key.equals( key ) ) {
				this.elements[index] = null;
				this.currentPopulation--;
				
				return temp.values;
			} else {
				// increment through the hashtable
				index = nextProbe( i, hashValue );
				i++;
			}
		}
		
		// item was not found
		return null;
	}
	
	/**
	 * Gets the item(s) associated with the String key.
	 * @param key The key corresponding to the desired value.
	 * @return The item(s) or null if it is not in the table.
	 */
	@SuppressWarnings("unchecked")
	public Vector<T> get( String key ) {
		int hashValue = elfHash( key ) % this.tableSize;
		int i = 1;
		int index = hashValue;
		
		while( this.elements[index] != null ) {
			if( key.equals( ( (Pair<T>) this.elements[index] ).key ) ) {
				return ((Pair<T>) this.elements[index] ).values;
			}
			
			index = nextProbe( i, hashValue );
			i++;
		}
		
		return null;
	}
	
	/**
	 * Gets the current table size.
	 * @return The table size.
	 */
	public int getCapacity() {
		return this.tableSize;
	}
	
	/**
	 * Gets the current number of items in the table.
	 * @return The current population.
	 */
	public int size() {
		return this.currentPopulation;
	}

	/*
	 * Helper function to increase the size of the table. Rehashes any existing items in the table.
	 */
	@SuppressWarnings("unchecked")
	private void grow() {
		for( int i = 0; i < TABLE_SIZES.length - 1; i++ ) {
			if( this.tableSize == TABLE_SIZES[i] ) {
				this.tableSize = TABLE_SIZES[i + 1];
				break;
			}
		}
		
		// reset population size; store the current table temporarily
		this.currentPopulation = 0;
		Object [] tempTable = this.elements;
		// allocate a new table
		this.elements = new Object[this.tableSize];
		// reinsert stuffs
		for( int i = 0; i < tempTable.length; i++ ) {
			if( tempTable[i] != null ) {
				Pair<T> pair = (Pair<T>) tempTable[i];
				for( T value : pair.values ) {
					insert( pair.key, value );
				}
			}
		}
	}
	
	/*
	 * Get the next value of in the probe sequence
	 */
	private int nextProbe( int k, int hashValue ) {
		return ( hashValue + ( ( k * k ) + k ) / 2 ) % this.tableSize;
	}
	
//	private int elfHash(String toHash) {
//		int hashValue = 0;
//		for (int Pos = 0; Pos < toHash.length(); Pos++) {      // use all elements
//		hashValue = (hashValue << 4) + toHash.charAt(Pos);  // shift/mix
//		int hiBits = hashValue & 0xF0000000;                // get high nybble
//		if (hiBits != 0)
//		hashValue ^= hiBits >> 24;    // xor high nybble with second nybble
//		hashValue &= ~hiBits;            // clear high nybble
//		}
//		return hashValue;
//		}
	
	private int elfHash( String key ) {
		int hash = 0;
		for( int i = 0; i < key.length(); i++ ) {
			hash = ( hash << 4 ) + key.charAt( i );
			int highBits = hash & 0xF0000000;
			
			if( highBits != 0 ) {
				hash ^= highBits >> 24;
			}
			
			// 2's complement; be certain it is a positive value
			hash &= ~highBits;
		}
		
		return hash;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Table size: " );
		sb.append( this.tableSize );
		sb.append( "\nCurrent population: " );
		sb.append( this.currentPopulation );
		sb.append( "\n\n" );
		
		for( int i = 0; i < this.tableSize; i++ ) {
			if( this.elements[i] != null ) {
				sb.append( i );
				sb.append( ":\t" );
				sb.append( "{" );
				sb.append( this.elements[i].toString() );
				sb.append( "}\n" );
			}
		}
		
		return sb.toString();
	}
	
	/*
	 * Nested class to contain a key and set of values.
	 */
	@SuppressWarnings("hiding")
	private class Pair<T> {
		private String key;
		private Vector<T> values;
		private Pair( String key, T value ) {
			this.key = key;
			this.values = new Vector<T>();
			this.values.add( value );
		}
		
		public String toString() {
			return this.key + ", " + this.values.toString();
		}
	}
}
