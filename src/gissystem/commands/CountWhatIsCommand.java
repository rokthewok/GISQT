package gissystem.commands;

import java.util.List;

import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;

/**
 * Object representing the "what_is -c" command. Gets the number of offsets associated with a given feature name and state.
 * @author John Q. Ruffer
 *
 */
public class CountWhatIsCommand implements ICommand {
	private String featureName;
	private String stateAbbreviation;
	
	/**
	 * ctor.
	 * @param featureName The name of the feature.
	 * @param stateAbbreviation The abbreviation of the state in which the feature resides.
	 */
	public CountWhatIsCommand( String featureName, String stateAbbreviation ) {
		this.featureName = featureName;
		this.stateAbbreviation = stateAbbreviation;
	}
	
	/**
	 * Executes the "what_is -c" command.
	 */
	/*
	 * 1. gets the list of offsets from the hashtable corresponding to the supplied feature name and state
	 * 2. writes the size of the list to the log. The size is the number of offsets.
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getHashTableController().findFeature( this.featureName, this.stateAbbreviation );
		
		controller.getLogger().writeToLog( "The number of features found with name \"" + this.featureName + "\" and state \"" + this.stateAbbreviation +"\" is: " );
		// the size of the offsets list is the number of offsets
		controller.getLogger().writeToLog( ( new Integer( offsets.size() ) ).toString() + "\n" );
	}

}
