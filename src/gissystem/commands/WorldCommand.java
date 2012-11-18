package gissystem.commands;

import gissystem.controllers.DataAccessController;
import gissystem.datastructures.PrQuadtree;
import gissystem.interfaces.ICommand;

/**
 * Creates a new PrQuadtree with the given coordinate boundaries
 * @author John
 *
 */
public class WorldCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	
	public WorldCommand( long xMin, long xMax, long yMin, long yMax ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	@Override
	public void execute( DataAccessController controller ) {
		PrQuadtree quadTree = new PrQuadtree( this.xMin, this.xMax, this.yMin, this.yMax );
		controller.getQuadTreeController().setQuadTree( quadTree );
		
		String message = writeFormattedWorldLog();
		
		controller.getLogger().writeToLog( message );
	}
	
	private String writeFormattedWorldLog() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Map boundaries:\n\t\t\t" );
		sb.append( this.yMax );
		sb.append( "\n\t" );
		sb.append( this.xMin );
		sb.append( "\t\t\t" );
		sb.append( this.xMax );
		sb.append( "\n\t\t\t" );
		sb.append( this.yMin );
		
		return sb.toString();
	}
}
