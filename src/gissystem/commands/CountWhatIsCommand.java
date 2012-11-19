package gissystem.commands;

import java.util.List;

import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

public class CountWhatIsCommand implements ICommand {
	private String featureName;
	private String stateAbbreviation;
	
	public CountWhatIsCommand( String featureName, String stateAbbreviation ) {
		this.featureName = featureName;
		this.stateAbbreviation = stateAbbreviation;
	}
	
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getHashTableController().findFeature( this.featureName, this.stateAbbreviation );
		
		controller.getLogger().writeToLog( "The number of features found with name \"" + this.featureName + "\" and state \"" + this.stateAbbreviation +"\" is: " );
		// the size of the offsets list is the number of offsets
		controller.getLogger().writeToLog( ( new Integer( offsets.size() ) ).toString() + "\n" );
	}

}
