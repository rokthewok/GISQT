package gissystem.models;

import gissystem.models.GeographicCoordinate;

/**
 * Represents a single record in the GIS data file. A Java Bean (POD).
 * @author John Ruffer
 *
 */
public class GeographicFeature {
	private Integer id;
	private String name;
	private String classification;
	private String alphabeticStateCode;
	private Integer numericStateCode;
	private String countyName;
	private Integer numericCountyCode;
	private GeographicCoordinate primaryLatitude;
	private GeographicCoordinate primaryLongitude;
	private GeographicCoordinate sourceLatitude;
	private GeographicCoordinate sourceLongitude;
	private Integer elevationInMeters;
	private Integer elevationInFeet;
	private String mapName;
	private String dateCreated;
	private String dateEdited;

	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getClassification() {
		return this.classification;
	}
	
	public String getAlphabeticStateCode() {
		return this.alphabeticStateCode;
	}
	
	public Integer getNumericStateCode() {
		return this.numericStateCode;
	}
	
	public String getCountyName() {
		return this.countyName;
	}
	
	public Integer getNumericCountyCode() {
		return this.numericCountyCode;
	}
	
	public GeographicCoordinate getPrimaryLatitude() {
		return this.primaryLatitude;
	}
	
	public GeographicCoordinate getPrimaryLongitude() {
		return this.primaryLongitude;
	}
	
	public GeographicCoordinate getSourceLatitude() {
		return this.sourceLatitude;
	}
	
	public GeographicCoordinate getSourceLongitude() {
		return this.sourceLongitude;
	}
	
	public Integer getElevationInMeters() {
		return this.elevationInMeters;
	}
	
	public Integer getElevationInFeet() {
		return this.elevationInFeet;
	}
	
	public String getMapName() {
		return this.mapName;
	}
	
	public String getDateCreated() {
		return this.dateCreated;
	}
	
	public String getDateEdited() {
		return this.dateEdited;
	}
	
	public void setId( Integer id ) {
		this.id = id;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public void setClassification( String classification ) {
		this.classification = classification;
	}
	
	public void setAlphabeticStateCode( String alphabeticStateCode ) {
		this.alphabeticStateCode = alphabeticStateCode;
	}
	
	public void setNumericStateCode( Integer numericStateCode ) {
		this.numericStateCode = numericStateCode;
	}
	
	public void setCountyName( String countyName ) {
		this.countyName = countyName;
	}
	
	public void setNumericCountyCode( Integer numericCountyCode ) {
		this.numericCountyCode = numericCountyCode;
	}
	
	public void setPrimaryLatitude( GeographicCoordinate primaryLatitude ) {
		this.primaryLatitude = primaryLatitude;
	}
	
	public void setPrimaryLongitude( GeographicCoordinate primaryLongitude ) {
		this.primaryLongitude = primaryLongitude;
	}
	
	public void setSourceLatitude( GeographicCoordinate sourceLatitude ) {
		this.sourceLatitude = sourceLatitude;
	}
	
	public void setSourceLongitude( GeographicCoordinate sourceLongitude ) {
		this.sourceLongitude = sourceLongitude;
	}
	
	public void setElevationInMeters( Integer elevationInMeters ) {
		this.elevationInMeters = elevationInMeters;
	}
	
	public void setElevationInFeet( Integer elevationInFeet ) {
		this.elevationInFeet = elevationInFeet;
	}
	
	public void setMapName( String mapName ) {
		this.mapName = mapName;
	}
	
	public void setDateCreated( String dateCreated ) {
		this.dateCreated = dateCreated;
	}
	
	public void setDateEdited( String dateEdited ) {
		this.dateEdited = dateEdited;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "ID: " );
		sb.append( this.id );
		sb.append( "\nName: " );
		sb.append( this.name );
		sb.append( "\nClassification: ");
		sb.append( this.classification );
		sb.append( "\nAlphabetic State Code: " );
		sb.append( this.alphabeticStateCode );
		sb.append( "\nNumeric State Code: " );
		sb.append( this.numericStateCode );
		sb.append( "\nCounty Name: " );
		sb.append( this.countyName );
		sb.append( "\nNumeric County Code: " );
		sb.append( this.numericCountyCode );
		sb.append( "\nPrimary Latitude: " );
		sb.append( this.primaryLatitude );
		sb.append( "\nPrimary Longitude: " );
		sb.append( this.primaryLongitude );
		sb.append( "\nSource Latitude: " );
		sb.append( this.sourceLatitude );
		sb.append( "\nSource Longitude: " );
		sb.append( this.sourceLongitude );
		sb.append( "\nElevation in Meters: " );
		sb.append( this.elevationInMeters );
		sb.append( "\nElevation in Feet: " );
		sb.append( this.elevationInFeet );
		sb.append( "\nMap Name: " );
		sb.append( this.mapName );
		sb.append( "\nDate Created: " );
		sb.append( this.dateCreated );
		sb.append( "\nDate Edited: " );
		sb.append( this.dateEdited );
		
		return sb.toString();
	}
	
	@Override
	public boolean equals( Object aThat ) {
		if( aThat == null ) {
			return false;
		}
		
		if( this == aThat ) {
			return true;
		}
		
		if( !( aThat instanceof GeographicFeature ) ) {
			return false;
		}
		
		GeographicFeature that = (GeographicFeature) aThat;
		return this.equalsHelper( that );
	}
	
	private boolean equalsHelper( GeographicFeature that ) {
		boolean equal = true;
		if( this.alphabeticStateCode != null ) equal = equal && this.alphabeticStateCode.equals( that.alphabeticStateCode );
		if( this.classification != null ) equal = equal && this.classification.equals( that.classification );
		if( this.countyName != null ) equal = equal && this.countyName.equals( that.countyName );
		if( this.dateCreated != null ) equal = equal && this.dateCreated.equals( that.dateCreated );
		if( this.dateEdited != null ) equal = equal && this.dateEdited.equals( that.dateEdited );
		if( this.elevationInFeet != null ) equal = equal && this.elevationInFeet.equals( that.elevationInFeet );
		if( this.elevationInMeters != null ) equal = equal && this.elevationInMeters.equals( that.elevationInMeters );
		if( this.id != null ) equal = equal && this.id.equals( that.id );
		if( this.numericCountyCode != null ) equal = equal && this.numericCountyCode.equals( that.numericCountyCode );
		if( this.numericStateCode != null ) equal = equal && this.numericStateCode.equals( that.numericStateCode );
		if( this.name != null ) equal = equal && this.name.equals( that.name );
		if( this.mapName != null ) equal = equal && this.mapName.equals( that.mapName );
		if( this.primaryLatitude != null ) equal = equal && this.primaryLatitude.equals( that.primaryLatitude );
		if( this.primaryLongitude != null ) equal = equal && this.primaryLongitude.equals( that.primaryLongitude );
		if( this.sourceLatitude != null ) equal = equal && this.sourceLatitude.equals( that.sourceLatitude );
		if( this.sourceLongitude != null ) equal = equal && this.sourceLongitude.equals( that.sourceLongitude );
		
		return equal;
	}
}

