package gissystem.commands;

import java.util.List;

import gissystem.commands.helpers.WhatIsInFormatter;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

/**
 * Object representation of the "what_is_in" command. Accepts an object implementing the IFormatter interface, which writes logs of varying verbosity.
 * @author John Ruffer
 *
 */
public class WhatIsInCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	private IFormatter formatter;
	
	/**
	 * ctor.
	 * @param xMin The lower x-bound of the rectangular region to be searched.
	 * @param xMax The upper x-bound of the rectangular region to be searched.
	 * @param yMin The lower y-bound of the rectangular region to be searched.
	 * @param yMax The upper y-bound of the rectangular region to be searched.
	 */
	public WhatIsInCommand( long xMin, long xMax, long yMin, long yMax ) {
		this( xMin, xMax, yMin, yMax, new WhatIsInFormatter() );
	}
	
	/**
	 * ctor.
	 * @param xMin The lower x-bound of the rectangular region to be searched.
	 * @param xMax The upper x-bound of the rectangular region to be searched.
	 * @param yMin The lower y-bound of the rectangular region to be searched.
	 * @param yMax The upper y-bound of the rectangular region to be searched.
	 * @param formatter The formatter for GeographicCoordinate Strings to be written to the log.
	 */
	public WhatIsInCommand( long xMin, long xMax, long yMin, long yMax, IFormatter formatter ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.formatter = formatter;
	}
	
	/**
	 * Executes the "what_is_in" command, either normal or with the "-l" flag.
	 */
	/*
	 * 1. get list of offsets in specified region from the quadtree
	 * 2. for each offset in offsets:
	 * 		3. get the record from the database
	 * 		4. create a GeographicFeature object from the record
	 * 		5. write the feature information to the log
	 * 6. end for
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( this.xMin, this.xMax, this.yMin, this.yMax );
		
		controller.getLogger().writeToLog( "The features found in the rectangle (" + this.xMin + ", " + this.yMin + "), (" + this.xMax + ", " + this.yMax + ") are:\n" );
		if( offsets.isEmpty() ) {
			controller.getLogger().writeToLog( "\tno results." );
		} else {
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( this.formatter.formatFeatureOutput( offset, feature ) );
				controller.getLogger().writeToLog( "\n" );
			}
		}
	}
}
