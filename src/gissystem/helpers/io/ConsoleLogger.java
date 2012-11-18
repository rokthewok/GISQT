package gissystem.helpers.io;

import gissystem.interfaces.ILogger;

/**
 * Simple logging class which writes any logs to stdout.
 * @author John Ruffer
 *
 */
public class ConsoleLogger implements ILogger {

	/**
	 * Writes the given message to the log.
	 * @param The message to be printed to the console.
	 */
	public void writeToLog( String message ) {
		System.out.print( message );
	}
}
