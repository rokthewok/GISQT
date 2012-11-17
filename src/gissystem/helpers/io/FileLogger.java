package gissystem.helpers.io;

import java.io.IOException;
import java.io.RandomAccessFile;

import gissystem.interfaces.ILogger;

/**
 * A simple logging class which writes to the given RandomAccessFile.
 * @see ILogger
 * @author John Ruffer
 *
 */
public class FileLogger
	implements ILogger {
	private RandomAccessFile log;
	
	/**
	 * Ctor.
	 * @param log the RandomAccessFile to which logs will be written.
	 */
	public FileLogger( RandomAccessFile log ) {
		this.log = log;
	}
	
	/**
	 * Writes the given message to the log.
	 * @param message The message to be written.
	 */
	public void writeToLog( String message ) {
		try {
			this.log.writeBytes( message );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}