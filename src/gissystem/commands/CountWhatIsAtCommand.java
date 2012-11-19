package gissystem.commands;

import gissystem.factories.GeographicCoordinateFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.models.GeographicPoint;

public class CountWhatIsAtCommand implements ICommand {
	private String rawLatitude;
	private String rawLongitude;
	
	public CountWhatIsAtCommand( String rawLatitude, String rawLongitude ) {
		this.rawLatitude = rawLatitude;
		this.rawLongitude = rawLongitude;
	}
	
	@Override
	public void execute(IDataAccessController controller) {
		// TODO Auto-generated method stub
		GeographicPoint point = new GeographicPoint( GeographicCoordinateFactory.createCoordinate( this.rawLatitude, "0.0" ),
				GeographicCoordinateFactory.createCoordinate( this.rawLongitude, "0.0" ) );
		
		controller.getLogger().writeToLog( "The number of offsets found at " + this.rawLongitude + ", " + this.rawLatitude + " is: " );
		// find the offests in the quadtree corresponding to the given point, print the size of the list of offsets
		controller.getLogger().writeToLog( ( new Integer( controller.getQuadTreeController().findInQuadTree( point ).size() ) ).toString() );
	}
}