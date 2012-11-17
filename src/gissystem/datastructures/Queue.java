package gissystem.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
	private List<T> elements;
	private int capacity;
	
	public Queue() {
		this( 20 );
	}
	
	public Queue( int capacity ) {
		this.elements = new ArrayList<T>( capacity );
		this.capacity = capacity;
	}
	
	public boolean offer( T element ) {
		if( this.elements.size() == this.capacity ) {
			return false;
		}
		
		this.elements.add( element );
		return true;
	}
	
	/**
	 * Force an item into the queue
	 * @param element the item to put in the queue
	 * @return the element that was forced out or null, if under capacity
	 */
	public T insist( T element ) {
		T lru = null;
		if( this.elements.size() == this.capacity ) {
			lru = this.elements.remove( 0 );
		}
		
		this.elements.add( element );
		return lru;
	}
	
	public T peek() {
		return this.elements.get( 0 );
	}
	
	public T poll() {
		return this.elements.remove( 0 );
	}
	
	public List<T> toList() {
		return this.elements;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int size() {
		return this.elements.size();
	}
	
	@Override
	public String toString() {
		return this.elements.toString();
	}
}
