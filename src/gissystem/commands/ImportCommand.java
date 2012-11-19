package gissystem.commands;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import gissystem.interfaces.IDataAccessController;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.models.GeographicFeature;
import gissystem.models.GeographicPoint;

public class ImportCommand implements ICommand {
	private String filename;
	
	public ImportCommand( String filename ) {
		this.filename = filename;
	}
	
	@Override
	public void execute( IDataAccessController controller ) {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
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
