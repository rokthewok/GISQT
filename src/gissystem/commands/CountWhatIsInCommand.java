package gissystem.commands;

import java.util.List;

import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

/**
 * Object representation of the "what_is_in -c" command and option. 
 * @author John "Lightning Fingers" Ruffer
 *
 */
public class CountWhatIsInCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	
	/**
	 * ctor.
	 * @param xMin the lower x-bound of the rectangular region.
	 * @param xMax the upper x-bound of the rectangular region.
	 * @param yMin the lower y-bound of the rectangular region.
	 * @param yMax the upper y-bound of the rectangular region.
	 */
	public CountWhatIsInCommand( long xMin, long xMax, long yMin, long yMax ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	/**
	 * Executes the "what_is_in -c" command and option.
	 */
	/*
	 * 1. gets a list of offsets corresponding to the region from the quadtree.
	 * 2. writes the length of the list to the log. The length corresponds to the number of offsets.
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( this.xMin, this.xMax, this.yMin, this.yMax );
		
		controller.getLogger().writeToLog( "The number of offsets within the rectangle (" + this.xMin + ", " + this.yMin + "), (" + this.xMax + ", " + this.yMax + ") " + "is: " );
		controller.getLogger().writeToLog( ( new Integer( offsets.size() ) ).toString() + "\n" );
	}
}
