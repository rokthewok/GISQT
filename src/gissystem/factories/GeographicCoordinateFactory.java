package gissystem.factories;

import gissystem.models.GeographicCoordinate;

/**
 * Creates GeographicCoordinates based on a string formatted in accordance with the GIS record
 * formats.
 * @see GeographicCoordinate
 * @author John Ruffer
 *
 */
public class GeographicCoordinateFactory {
	
	/**
	 * A wrapper for the two parameter method.
	 * @param coordinateToParse The String representation of the coordinate, in DMS format.
	 * @return A GeographicCoordinate.
	 */
	public static GeographicCoordinate createCoordinate( String coordinateToParse ) {
		return createCoordinate( coordinateToParse, "0.0" );
	}
	
	/**
	 * Creates a coordinate.
	 * @param coordinateToParse The String representation of the coordinate.
	 * @param decimalValueString The String representation of the decimal coordinate value.
	 * @return a GeographicCoordinate with the appropriate values.
	 */
	public static GeographicCoordinate createCoordinate( String coordinateToParse, String decimalValueString ) {
		
		if( coordinateToParse.isEmpty() ) {
			return null;
		}
		
		String latitudePoints = "NS";
		String longitudePoints = "EW";
		
		if( latitudePoints.contains( coordinateToParse.substring( coordinateToParse.length() - 1 ) ) ) {
			return createLatitudeCoordinate( coordinateToParse, decimalValueString );
		}
		
		else if( longitudePoints.contains( coordinateToParse.substring( coordinateToParse.length() - 1 ) ) ) {
			return createLongitudeCoordinate( coordinateToParse, decimalValueString );
		}
		
		else {
			return null;
		}
	}
	
	/**
	 * Parses the string according to the Latitude format.
	 * @param coordinateToParse The coordinate to create.
	 * @param decimalValueString The decimal coordinate value.
	 * @return
	 */
	private static GeographicCoordinate createLatitudeCoordinate( String coordinateToParse, String decimalValueString ) {
		String substring = coordinateToParse.substring( 0, coordinateToParse.length() - 1 );
		
		Integer degrees = Integer.parseInt( substring.substring( 0, substring.length() - 4 ) );
		Integer minutes = Integer.parseInt( substring.substring( substring.length() - 4, substring.length() - 2 ) );
		Integer seconds = Integer.parseInt( substring.substring( substring.length() - 2, substring.length() ) );
		String cardinalDirection = coordinateToParse.substring( coordinateToParse.length() - 1 );

		if( cardinalDirection.equals( "N" ) ) {
			cardinalDirection = "North";
		} else {
			cardinalDirection = "South";
		}
		
		Double decimalValue = !decimalValueString.isEmpty() ? Double.parseDouble( decimalValueString ) : null;
		
		return new GeographicCoordinate( degrees, minutes, seconds, cardinalDirection, decimalValue );
	}
	
	/**
	 * Parses the string according to the Longitude format.
	 * @param coordinateToParse The coordinate to create.
	 * @param decimalValueString The decimal coordinate value.
	 * @return
	 */
	private static GeographicCoordinate createLongitudeCoordinate( String coordinateToParse, String decimalValueString ) {
		String substring = coordinateToParse.substring( 0, coordinateToParse.length() - 1 );
		
		Integer degrees = Integer.parseInt( substring.substring( 0, substring.length() - 4 ) );
		Integer minutes = Integer.parseInt( substring.substring( substring.length() - 4, substring.length() - 2 ) );
		Integer seconds = Integer.parseInt( substring.substring( substring.length() - 2, substring.length() ) );
		String cardinalDirection = coordinateToParse.substring( coordinateToParse.length() - 1 );
		
		if( cardinalDirection.equals( "E" ) ) {
			cardinalDirection = "East";
		} else {
			cardinalDirection = "West";
		}
		
		Double decimalValue = !decimalValueString.isEmpty() ? Double.parseDouble( decimalValueString ) : null;
		
		return new GeographicCoordinate( degrees, minutes, seconds, cardinalDirection, decimalValue );
	}
}

