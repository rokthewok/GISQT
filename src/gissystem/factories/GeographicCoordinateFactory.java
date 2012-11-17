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
		Integer degrees = Integer.parseInt( coordinateToParse.substring( 0, 2) );
		Integer minutes = Integer.parseInt( coordinateToParse.substring( 2, 4) );
		Integer seconds = Integer.parseInt( coordinateToParse.substring( 4, 6) );
		String cardinalDirection = coordinateToParse.substring( 6 );

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
		Integer degrees = Integer.parseInt( coordinateToParse.substring( 0, 3) );
		Integer minutes = Integer.parseInt( coordinateToParse.substring( 3, 5) );
		Integer seconds = Integer.parseInt( coordinateToParse.substring( 5, 7) );
		String cardinalDirection = coordinateToParse.substring( 7 );
		
		if( cardinalDirection.equals( "E" ) ) {
			cardinalDirection = "East";
		} else {
			cardinalDirection = "West";
		}
		
		Double decimalValue = !decimalValueString.isEmpty() ? Double.parseDouble( decimalValueString ) : null;
		
		return new GeographicCoordinate( degrees, minutes, seconds, cardinalDirection, decimalValue );
	}
}

