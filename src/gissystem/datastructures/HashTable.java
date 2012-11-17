package gissystem.datastructures;

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
	
	public void insert( String key, T element ) {
		Pair<T> pair = new Pair<T>( key, element );
		int hashValue = elfHash( key ) % this.tableSize;
		
		int i = 1;
		int index = hashValue;
		while( this.elements[index] != null ) {
			index = ( hashValue + ( i * i + i ) / 2 ) % this.tableSize;
			i++;
		}
		
		this.elements[index] = pair;
		
		this.currentPopulation++;
		if( this.currentPopulation >= ( this.tableSize * 0.70 ) 
				&& this.tableSize != TABLE_SIZES[TABLE_SIZES.length - 1] ) {
			this.grow();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T remove( String key ) {
		int index = elfHash( key ) % this.tableSize;
		Pair<T> temp = (Pair<T>) this.elements[index];
		this.elements[index] = null;
		this.currentPopulation--;
		
		return temp.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public T get( String key ) {
		int index = elfHash( key ) % this.tableSize;
		if( this.elements[index] != null ) {
			return ((Pair<T>) this.elements[index] ).getValue();
		} else {
			return null;
		}
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
		sb.append( "[" );
		for( int i = 0; i < this.tableSize; i++ ) {
			sb.append( "{" );
			if( this.elements[i] != null ) {
				sb.append( this.elements[i].toString() );
			}
			sb.append( "} " );
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	@SuppressWarnings("hiding")
	private class Pair<T> {
		private String key;
		private T value;
		private Pair( String key, T value ) {
			this.key = key;
			this.value = value;
		}
		
		private String getKey() {
			return this.key;
		}
		
		private T getValue() {
			return this.value;
		}
		
		public String toString() {
			return this.key + ", " + this.value.toString();
		}
	}
}
