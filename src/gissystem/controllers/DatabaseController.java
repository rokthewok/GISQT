package gissystem.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import gissystem.datastructures.BufferPool;

public class DatabaseController {
	private BufferPool<Long, String> bufferPool;
	private RandomAccessFile database;
	
	public DatabaseController() throws FileNotFoundException {
		this( new File( "database.txt" ) );
	}
	
	public DatabaseController( File databaseFile ) throws FileNotFoundException {
		this.bufferPool = new BufferPool<Long, String>( 20 );
		this.database = new RandomAccessFile( databaseFile, "rw" );
	}
	
	public String get( Long offset ) {
		String searchResult = null;
		if( ( searchResult = this.bufferPool.get( offset ) ) != null ) {
			return searchResult;
		}
		
		// not in the buffer; resort to hitting the database file
		try {
			this.database.seek( offset );
			searchResult = this.database.readLine();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return searchResult;
	}
	
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
	
	public String bufferPoolToString() {
		return this.bufferPool.toString();
	}
}
