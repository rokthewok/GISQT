package gissystem.controllers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.ILogger;

public class DataAccessController implements IDataAccessController {
	private RandomAccessFile commandFile;
	private ILogger logFile;
	
	private DatabaseController databaseController;
	private QuadTreeController quadTreeController;
	private HashTableController hashTableController;
	
	public DataAccessController( File databaseFile, RandomAccessFile commandFile, ILogger logFile ) {
		this.commandFile = commandFile;
		this.logFile = logFile;
		this.databaseController = new DatabaseController( databaseFile );
	}
	
	@Override
	public String getNextCommandLine() {
		String line = null;
		try {
			line = this.commandFile.readLine();
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
		return line;
	}
	
	@Override
	public String findInDatabase( Long offset ) {
		return this.databaseController.get( offset );
	}
	
	@Override
	public QuadTreeController getQuadTreeController() {
		return this.quadTreeController;
	}
	
	@Override
	public HashTableController getHashTableController() {
		return this.hashTableController;
	}
	
	@Override
	public ILogger getLogger() {
		return this.logFile;
	}
}
