package gissystem.commands;

import java.util.List;

import gissystem.commands.helpers.WhatIsFormatter;
import gissystem.factories.GeographicFeatureFactory;
import gissystem.interfaces.ICommand;
import gissystem.interfaces.IDataAccessController;
import gissystem.interfaces.IFormatter;
import gissystem.models.GeographicFeature;

/**
 * Object representation of the "what_is" command.
 * @author John
 *
 */
public class WhatIsCommand implements ICommand {
	private String featureName;
	private String stateAbbreviation;
	private IFormatter formatter;
	
	/**
	 * ctor.
	 * @param featureName The name of the feature to be found.
	 * @param stateAbbreviation The abbreviation of the state in which the feature is located.
	 */
	public WhatIsCommand( String featureName, String stateAbbreviation ) {
		this( featureName, stateAbbreviation, new WhatIsFormatter() );
	}
	
	/**
	 * ctor.
	 * @param featureName The name of the feature to be found.
	 * @param stateAbbreviation The abbreviation of the state in which the feature is located.
	 * @param formatter The IFormatter implementation which writes Strings to be logged in varying verbosity.
	 */
	public WhatIsCommand( String featureName, String stateAbbreviation, IFormatter formatter ) {
		this.featureName = featureName;
		this.stateAbbreviation = stateAbbreviation;
		this.formatter = formatter;
	}
	
	/**
	 * Executes the "what_is" command, possibly with the "-l" option.
	 */
	/*
	 * 1. get a list of offsets using the feature name and state abbreviation from the hashtable.
	 * 2. for each offset in offsets:
	 * 		3. get the record from the db.
	 * 		4. create a GeographicFeature from the record.
	 * 		5. log using the formatter object.
	 *  6. endfor
	 */
	@Override
	public void execute( IDataAccessController controller ) {
		List<Long> offsets = controller.getHashTableController().findFeature( this.featureName, this.stateAbbreviation );
		
		if( offsets == null ) {
			controller.getLogger().writeToLog( "\tno results with name \"" + this.featureName + "\" and state \"" + this.stateAbbreviation + "\" were found.\n" );
		} else {
			controller.getLogger().writeToLog( "The following " + offsets.size() + " features were found with name \"" + 
								this.featureName + "\" and state \"" + this.stateAbbreviation +"\":\n" );
			for( Long offset : offsets ) {
				String record = controller.getDatabaseController().get( offset );
				GeographicFeature feature = GeographicFeatureFactory.createGeographicFeature( record );
				
				controller.getLogger().writeToLog( this.formatter.formatFeatureOutput( offset, feature ) );
				controller.getLogger().writeToLog( "\n" );
			}
		}
	}
}
