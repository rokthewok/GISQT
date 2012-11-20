package gissystem.commands;

import java.util.List;

import gissystem.commands.helpers.WhatIsInFormatter;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

public class WhatIsInCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	private IFormatter formatter;
	
	public WhatIsInCommand( long xMin, long xMax, long yMin, long yMax ) {
		this( xMin, xMax, yMin, yMax, new WhatIsInFormatter() );
	}
	
	public WhatIsInCommand( long xMin, long xMax, long yMin, long yMax, IFormatter formatter ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.formatter = formatter;
	}
	
	@Override
	public void execute( IDataAccessController controller ) {
		// TODO Auto-generated method stub
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( this.xMin, this.xMax, this.yMin, this.yMax );
		
		controller.getLogger().writeToLog( "The features found in the rectangle (" + this.xMin + ", " + this.yMin + "), (" + this.xMax + ", " + this.yMax + ") are:\n" );
		if( offsets.isEmpty() ) {
			controller.getLogger().writeToLog( "\tno results." );
		} else {
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( this.formatter.formatFeatureOutput( offset, feature ) );
			}
		}
	}
	
//	protected String formatFeatureOutput( Long offset, GeographicFeature feature ) {
//		StringBuilder sb = new StringBuilder();
//		sb.append( "\n\toffset:\t\t" );
//		sb.append( offset );
//		sb.append( "\n\tname:\t\t" );
//		sb.append( feature.getName() );
//		sb.append( "\n\tstate:\t\t" );
//		sb.append( feature.getAlphabeticStateCode() );
//		sb.append( "\n\tlatitude:\t" );
//		sb.append( feature.getPrimaryLatitude().toString() );
//		sb.append( "\n\tlongitude:\t" );
//		sb.append( feature.getPrimaryLongitude().toString() );
//		sb.append( "\n" );
//		
//		return sb.toString();
//	}
}
