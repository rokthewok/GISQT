package gissystem.models;

import gissystem.helpers.Direction;
import gissystem.interfaces.IPoint;

/**
 * An object representation of a GeographicPoint, containing a latitude and longitude. Implements the IPoint interface,
 * so it is compatible with the PrQuadtree.
 * @see IPoint
 * @author John Ruffuffer
 *
 */
public class GeographicPoint implements IPoint {
	private GeographicCoordinate latitude;
	private GeographicCoordinate longitude;
	
	/**
	 * ctor.
	 * @param latitude The latitude of the point.
	 * @param longitude The longitude of the point.
	 */
	public GeographicPoint( GeographicCoordinate latitude, GeographicCoordinate longitude ) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public long getX() {
		return this.longitude.toSeconds();
	}

	@Override
	public long getY() {
		return this.latitude.toSeconds();
	}

	/**
	 * Determines the direction which this coordinate is located from the given coordinates.
	 * @return The Direction enumerated type corresponding to this coordinate's relative location. 
	 */
	@Override
	public Direction directionFrom( long X, long Y ) {
		if( this.longitude.toSeconds() == X && this.latitude.toSeconds() == Y ) {
			// same point
			return Direction.NOQUADRANT;
		} else if( this.longitude.toSeconds() < X && this.latitude.toSeconds() <= Y ) {
			// to the Southwest of the other point
			return Direction.SW;
		} else if( this.longitude.toSeconds() >= X && this.latitude.toSeconds() < Y ) {
			// to the Southeast of the other point
			return Direction.SE;
		} else if( this.longitude.toSeconds() <= X && this.latitude.toSeconds() > Y ) {
			// to the Northwest of the other point
			return Direction.NW;
		} else {
			// to the Northeast of the other point
			return Direction.NE;
		}
	}

	/**
	 * Determines the quadrant in which the current point resides in the given region.
	 * @return the Direction corresponding to the appropriate quadrant. 
	 */
	@Override
	public Direction inQuadrant( double xLo, double xHi, double yLo, double yHi ) {
		if( this.longitude.toSeconds() > xHi || this.longitude.toSeconds() < xLo || this.latitude.toSeconds() > yHi || this.latitude.toSeconds() < yLo ) {
			// this coordinate is not in the specified region
			return Direction.NOQUADRANT;
		} else if( this.longitude.toSeconds() < ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() <= ( yHi - yLo ) / 2.0 ) {
			// coordinate is in the Southwest quadrant
			return Direction.SW;
		} else if( this.longitude.toSeconds() >= ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() < (yHi - yLo ) / 2.0 ) {
			// coordinate is in the Southeast quadrant
			return Direction.SE;
		} else if( this.longitude.toSeconds() <= ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() > ( yHi - yLo) / 2.0 ) {
			// coordinate is in the Northwest quadrant
			return Direction.NW;
		} else {
			// coordinate is in the Northeast quadrant
			return Direction.NE;
		}
	}

	/**
	 * Determines if the current coordinate is in a given region.
	 * @return true if it is in the region, else false.
	 */
	@Override
	public boolean inBox( double xLo, double xHi, double yLo, double yHi ) {
		return ( this.longitude.toSeconds() > xLo && this.longitude.toSeconds() < xHi ) && 
				( this.latitude.toSeconds() > yLo && this.latitude.toSeconds() < yHi );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Point: " );
		sb.append( this.longitude.toSeconds() );
		sb.append( ", " );
		sb.append( this.latitude.toSeconds() );
		return sb.toString();
	}
	
	@Override
	public boolean equals( Object other ) {
		if( this == other ) {
			return true;
		}
		
		if( !( other instanceof GeographicPoint ) ) {
			return false;
		}
		
		GeographicPoint that = (GeographicPoint) other;
		
		return this.longitude.toSeconds() == that.longitude.toSeconds() && this.latitude.toSeconds() == that.latitude.toSeconds(); 
	}
}
