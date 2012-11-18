package gissystem.controllers;

import java.io.RandomAccessFile;
import java.util.List;

import gissystem.datastructures.HashTable;
import gissystem.datastructures.PrQuadtree;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.ILogger;
import gissystem.models.GeographicCoordinate;

public class DataAccessController implements IDataAccessController {
	private ILogger databaseFile;
	private RandomAccessFile commandFile;
	private ILogger logFile;
	
	private QuadTreeController quadTreeController;
	private HashTableController hashTableController;
	
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
	
	public QuadTreeController getQuadTreeController() {
		return this.quadTreeController;
	}
	
	public HashTableController getHashTableController() {
		return this.hashTableController;
	}
	
	public ILogger getLogger() {
		return this.logFile;
	}
}
