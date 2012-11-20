package gissystem.controllers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.ILogger;

/**
 * Provides access to various data models in the GIS System.
 * Contains HashTableController, QuadTreeController, DatabaseController, Logger, and CommandFile.
 * @author John Ruffer
 *
 */
public class DataAccessController implements IDataAccessController {
	private RandomAccessFile commandFile;
	private ILogger logFile;
	
	private DatabaseController databaseController;
	private QuadTreeController quadTreeController;
	private HashTableController hashTableController;
	
	/**
	 * ctor.
	 * @param databaseFile The File where records will be written and accessed.
	 * @param commandFile The RandomAccessFile from which commands will be retrieved.
	 * @param logFile The Logger where result logging will take place.
	 */
	public DataAccessController( File databaseFile, RandomAccessFile commandFile, ILogger logFile ) {
		this.commandFile = commandFile;
		this.logFile = logFile;
		this.databaseController = new DatabaseController( databaseFile );
		this.quadTreeController = new QuadTreeController();
		this.hashTableController = new HashTableController();
	}
	
	/**
	 * Gets the next line from the commands file.
	 * @return The next line in the command file, or null if the file has reached an end.
	 */
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
	
	/**
	 * Getter for QuadTreeController.
	 * @return The QuadTreeController.
	 */
	@Override
	public QuadTreeController getQuadTreeController() {
		return this.quadTreeController;
	}
	
	/**
	 * Getter for HashTableController.
	 * @return The HashTableController.
	 */
	@Override
	public HashTableController getHashTableController() {
		return this.hashTableController;
	}
	
	/**
	 * Getter for the DatabaseController.
	 * @return The DatabaseController.
	 */
	@Override
	public DatabaseController getDatabaseController() {
		return this.databaseController;
	}
	
	/**
	 * Getter for the ILogger.
	 * @return The ILogger.
	 */
	@Override
	public ILogger getLogger() {
		return this.logFile;
	}
}
