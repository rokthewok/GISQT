package gissystem.controllers;

import gissystem.datastructures.HashTable;

import java.util.List;

/**
 * A wrapper for function calls to the HashTable.
 * @author John
 *
 */
public class HashTableController {
	private HashTable<Long> table;
	
	public HashTableController() {
		this.table = new HashTable<Long>();
	}
	
	/**
	 * Inserts an offset with the given feature name and state into the hashtable.
	 * @param featureName The name of the feature being inserted.
	 * @param stateAbbreviation The state where the feature being inserted is located.
	 * @param offset the database offset of the feature.
	 * @return The number of probes made by the table or -1 if the offset is already in the table.
	 */
	public int addFeature( String featureName, String stateAbbreviation, Long offset ) {
		String key = featureName + ":" + stateAbbreviation;
		return this.table.insert( key, offset );
	}
	
	/**
	 * Retrieves all the features with the provided feature name and state.
	 * @param featureName The name of the feature being inserted.
	 * @param stateAbbreviation The state where the feature being inserted is located.
	 * @return A List of all the database offsets with the corresponding feature name and state.
	 */
	public List<Long> findFeature( String featureName, String stateAbbreviation ) {
		String key = featureName + ":" + stateAbbreviation;
		return this.table.get( key );
	}
	
	public String getHashTableToString() {
		return this.table.toString();
	}
}
