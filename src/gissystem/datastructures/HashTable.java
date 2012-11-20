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
			if( ( (Pair<T>) this.elements[index] ).getKey().equals( key ) ) {
				// check if the item is already in the table
				if( ( (Pair<T>) this.elements[index] ).getValues().contains( element ) ) {
					return -1;
				}
				
				( (Pair<T>) this.elements[index] ).getValues().add( element );
				
				inserted = true;
				break;
			}
			index = ( hashValue + ( i * i + i ) / 2 ) % this.tableSize;
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
	
	@SuppressWarnings("unchecked")
	public Vector<T> remove( String key ) {
		int index = elfHash( key ) % this.tableSize;
		Pair<T> temp = (Pair<T>) this.elements[index];
		this.elements[index] = null;
		this.currentPopulation--;
		
		return temp.getValues();
	}
	
	@SuppressWarnings("unchecked")
	public Vector<T> get( String key ) {
		int index = elfHash( key ) % this.tableSize;
		int i = 1;
		while( this.elements[index] != null ) {
			if( key.equals( ( (Pair<T>) this.elements[index] ).getKey() ) ) {
				return ((Pair<T>) this.elements[index] ).getValues();
			}
			
			index = ( index + ( i * i + i ) / 2 ) % this.tableSize;
		}
		
		return null;
	}
	
	public int getCapacity() {
		return this.tableSize;
	}
	
	public int size() {
		return this.currentPopulation;
	}

	
	@SuppressWarnings("unchecked")
	private void grow() {
		for( int i = 0; i < TABLE_SIZES.length - 1; i++ ) {
			if( this.tableSize == TABLE_SIZES[i] ) {
				this.tableSize = TABLE_SIZES[i + 1];
				break;
			}
		}
		
		Object [] newTable = new Object[this.tableSize];
		for( int i = 0; i < this.elements.length; i++ ) {
			if( this.elements[i] != null ) {
				int index = elfHash( ( (Pair<T>) this.elements[i] ).getKey() ) % this.tableSize;
				newTable[index] = this.elements[i];
			}
		}
		
		this.elements = newTable;
	}
	
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
	
	@SuppressWarnings("hiding")
	private class Pair<T> {
		private String key;
		private Vector<T> values;
		private Pair( String key, T value ) {
			this.key = key;
			this.values = new Vector<T>();
			this.values.add( value );
		}
		
		private String getKey() {
			return this.key;
		}
		
		private Vector<T> getValues() {
			return this.values;
		}
		
		public String toString() {
			return this.key + ", " + this.values.toString();
		}
	}
}
