package gissystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import gissystem.controllers.DataAccessController;
import gissystem.controllers.GisSystemController;
import gissystem.helpers.io.FileLogger;

public class GisSystem {
	
	public static void main( String [] args ) {
		RandomAccessFile commandFile = null;
		RandomAccessFile logFile = null;
		try {
			commandFile = new RandomAccessFile( "Script01.txt", "r" );
			logFile = new RandomAccessFile( "log.txt", "rw" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File databaseFile = new File( "database.txt" );
		if( commandFile != null && logFile != null ) {
			GisSystemController controller = new GisSystemController( new DataAccessController( databaseFile, commandFile, new FileLogger( logFile ) ) );
			controller.doWork();
		}
	}
}
