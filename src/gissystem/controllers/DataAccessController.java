package gissystem.controllers;

import java.io.RandomAccessFile;
import java.util.List;

import gissystem.datastructures.HashTable;
import gissystem.datastructures.PrQuadtree;
import gissystem.interfaces.ILogger;
import gissystem.models.GeographicCoordinate;

public class DataAccessController {
	private ILogger databaseFile;
	private RandomAccessFile commandFile;
	private ILogger logFile;
	
	public DataAccessController( ILogger databaseFile, RandomAccessFile commandFile, ILogger logFile ) {
		this.databaseFile = databaseFile;
		this.commandFile = commandFile;
		this.logFile = logFile;
	}
	
	public String getNextCommandLine() {
		return null;
	}
	
	public String findInDatabase( Integer offset ) {
		return null;
	}
	
	public PrQuadtree getQuadTree() {
		return null;
	}
	
	public List<Integer> findByCoordinates( GeographicCoordinate latitude, GeographicCoordinate longitude ) {
		return null;
	}
	
	public List<Integer> findByRegion( GeographicCoordinate centerLatitude, GeographicCoordinate centerLongitude, int height, int width ) {
		return null;
	}
	
	public Integer findByNameAndState( String featureName, String state ) {
		return null;
	}
	public HashTable<Long> getHashTable() {
		return null;
	}
}
