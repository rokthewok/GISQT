package gissystem.commands;

import gissystem.interfaces.IDataAccessController;
import gissystem.datastructures.PrQuadtree;
import gissystem.interfaces.ICommand;

/**
 * Creates a new PrQuadtree with the given world coordinate boundaries.
 * @author John "not Quincy" Quentin Ruffer
 *
 */
public class WorldCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	
	/**
	 * ctor.
	 * @param xMin The farthest West boundary of the world map. Expressed in seconds.  
	 * @param xMax The farthest East boundary of the world map. Expressed in seconds.
	 * @param yMin The farthest South boundary of the world map. Expressed in seconds.
	 * @param yMax The farthest North boundary of the world map. Expressed in seconds.
	 */
	public WorldCommand( long xMin, long xMax, long yMin, long yMax ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	/**
	 * Executes the "world" command.
	 */
	/*
	 * 1. create a new PrQuadtree using the supplied boundary coordinates.
	 * 2. set the quadtree in the QuadTreeController (in the DataAccessController) to this quadtree.
	 * 3. log things.
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		PrQuadtree quadTree = new PrQuadtree( this.xMin, this.xMax, this.yMin, this.yMax, 4 ); // bucket size 4
		controller.getQuadTreeController().setQuadTree( quadTree );
		
		String message = writeFormattedWorldLog();
		
		controller.getLogger().writeToLog( message );
	}
	
	private String writeFormattedWorldLog() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Map boundaries:\n" );
		
		for( int i = 0; i < 30 - ( new Long( this.yMax ) ).toString().length(); i++ ) {
			sb.append( " " );
		}
		sb.append( this.yMax );
		sb.append( "\n" );
		
		for( int i = 0; i < 15 - ( new Long( this.xMin ) ).toString().length(); i++ ) {
			sb.append( " " );
		}
		sb.append( this.xMin );
		
		for( int i = 0; i < 30 - ( new Long( this.xMax ) ).toString().length(); i++ ) {
			sb.append( " " );
		}
		sb.append( this.xMax );
		sb.append( "\n" );
		
		for( int i = 0; i < 30 - ( new Long( this.yMin ) ).toString().length(); i++ ) {
			sb.append( " " );
		}
		sb.append( this.yMin );
		
		sb.append( "\n" );
		
		return sb.toString();
	}
}
