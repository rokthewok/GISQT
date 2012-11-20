package gissystem.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import gissystem.datastructures.BufferPool;

/**
 * Controls access to various components of the database, e.g. the bufferpool and the database file.
 * @author John "Malkovich Malkovich" Ruffer
 *
 */
public class DatabaseController {
	private BufferPool<Long, String> bufferPool;
	private RandomAccessFile database;
	
	public DatabaseController() {
		this( new File( "database.txt" ) );
	}
	
	/**
	 * ctor.
	 * @param databaseFile The file to be used for the database.
	 */
	public DatabaseController( File databaseFile ) {
		this.bufferPool = new BufferPool<Long, String>( 20 );
		// be sure to truncate the file
		databaseFile.delete();
		try {
			this.database = new RandomAccessFile( databaseFile, "rw" );
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the record located at the specified offset.
	 * @param offset The offset of the record in the database.
	 * @return The raw String representation of the record, or null if the record was not located.
	 */
	public String get( Long offset ) {
		String searchResult = null;
		if( ( searchResult = this.bufferPool.get( offset ) ) != null ) {
			return searchResult;
		}
		
		// not in the buffer; resort to hitting the database file
		try {
			this.database.seek( offset );
			searchResult = this.database.readLine();
			this.bufferPool.add( offset, searchResult );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return searchResult;
	}
	
	/**
	 * Adds a feature to the database. The feature to be added is first sent through the BufferPool, then added to the database.
	 * @param rawFeature The String representatoin of the feature to be added.
	 */
	public void add( String rawFeature ) {
		try {
			// the new offset of the feature being added will be the length - 1 of the database file
			this.bufferPool.add( this.database.length(), rawFeature );
			this.database.seek( this.database.length() );
			this.database.writeBytes( rawFeature );
			this.database.writeBytes( "\n" );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the length of the database. Good for choosing longest offset of the db.
	 * @return The length of the database file or -1 if the check is unsuccessful.
	 */
	public long getDatabaseLength() {
		long length = -1L;
		try {
			length = this.database.length();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return length;
	}
	
	/**
	 * Wrapper method for the bufferpool toString() method.
	 * @return The String representation of the bufferpool.
	 */
	public String getBufferPoolToString() {
		return this.bufferPool.toString();
	}
}
