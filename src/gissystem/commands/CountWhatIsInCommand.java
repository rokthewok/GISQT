package gissystem.commands;

import java.util.List;

import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class CountWhatIsInCommand implements ICommand {
	private long xMin;
	private long xMax;
	private long yMin;
	private long yMax;
	
	public CountWhatIsInCommand( long xMin, long xMax, long yMin, long yMax ) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getQuadTreeController().findInQuadTree( this.xMin, this.xMax, this.yMin, this.yMax );
		
		controller.getLogger().writeToLog( "The number of offsets within the rectangle (" + this.xMin + ", " + this.yMin + "), (" + this.xMax + ", " + this.yMax + ") " + "is: " );
		controller.getLogger().writeToLog( ( new Integer( offsets.size() ) ).toString() + "\n" );
	}
}
