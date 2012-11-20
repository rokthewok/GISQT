package gissystem.commands;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import gissystem.interfaces.IDataAccessController;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.models.GeographicFeature;
import gissystem.models.GeographicPoint;

/**
 * Object representation of the "import" command. Imports a record file into the database file.
 * @author John Quentin Ruffer
 *
 */
public class ImportCommand implements ICommand {
	private String filename;
	
	/**
	 * ctor
	 * @param filename The name of the records file to import.
	 */
	public ImportCommand( String filename ) {
		this.filename = filename;
	}
	
	/**
	 * Executes the "import" command.
	 */
	/*
	 * 1. read the first line to get rid of heading
	 * 2. for each line in records:
	 * 		3. store the offset of the line
	 * 		4. create GeographicFeature from line
	 * 		5. insert feature offset in Quadtree
	 * 		6. insert feature offset in hashtable
	 * 		7. add line to the db
	 * 8. endfor
	 * 9. log the # of entries and longest hashtable probe sequence
	 */ 
	@Override
	public void execute( IDataAccessController controller ) {
		try {
			RandomAccessFile recordsFile = new RandomAccessFile( new File( this.filename ), "r" );
			
			// get rid of the first line of headings
			String line = recordsFile.readLine();
			long offset = 0L;
			// counters for logging
			int count = 0;
			int maxProbe = 0;
			while( ( line = recordsFile.readLine() ) != null ) {
				offset = controller.getDatabaseController().getDatabaseLength();
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( line );
				
				GeographicPoint point = new GeographicPoint( feature.getPrimaryLatitude(), feature.getPrimaryLongitude() );
				// insert into Quadtree and Hashtable
				controller.getQuadTreeController().insertToQuadTree( point, offset );
				int probe = controller.getHashTableController().addFeature( feature.getName(), feature.getAlphabeticStateCode(), offset );
				// add to database
				controller.getDatabaseController().add( line );
				
				if( probe > maxProbe ) {
					maxProbe = probe;
				}
				count++;
			}
			
			controller.getLogger().writeToLog( formatLogMessage( count, maxProbe ) );
			recordsFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatLogMessage( int count, int maxProbe ) {
		StringBuilder sb = new StringBuilder();
		sb.append( "\tnumber of entries added: " );
		sb.append( count );
		sb.append( "\n" );
		sb.append( "\tlongest hashtable probe sequence: " );
		sb.append( maxProbe );
		sb.append( "\n" );
		
		return sb.toString();
	}
}
