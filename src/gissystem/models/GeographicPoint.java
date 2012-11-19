package gissystem.models;

import gissystem.helpers.Direction;
import gissystem.interfaces.IPoint;

public class GeographicPoint implements IPoint {
	private GeographicCoordinate latitude;
	private GeographicCoordinate longitude;
	
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

	@Override
	public Direction directionFrom( long X, long Y ) {
		if( this.longitude.toSeconds() == X && this.latitude.toSeconds() == Y ) {
			return Direction.NOQUADRANT;
		} else if( this.longitude.toSeconds() < X && this.latitude.toSeconds() <= Y ) {
			return Direction.SW;
		} else if( this.longitude.toSeconds() >= X && this.latitude.toSeconds() < Y ) {
			return Direction.SE;
		} else if( this.longitude.toSeconds() <= X && this.latitude.toSeconds() > Y ) {
			return Direction.NW;
		} else {
			return Direction.NE;
		}
	}

	@Override
	public Direction inQuadrant( double xLo, double xHi, double yLo, double yHi ) {
		if( this.longitude.toSeconds() > xHi || this.longitude.toSeconds() < xLo || this.latitude.toSeconds() > yHi || this.latitude.toSeconds() < yLo ) {
			return Direction.NOQUADRANT;
		} else if( this.longitude.toSeconds() < ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() <= ( yHi - yLo ) / 2.0 ) {
			return Direction.SW;
		} else if( this.longitude.toSeconds() >= ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() < (yHi - yLo ) / 2.0 ) {
			return Direction.SE;
		} else if( this.longitude.toSeconds() <= ( xHi - xLo ) / 2.0 && this.latitude.toSeconds() > ( yHi - yLo) / 2.0 ) {
			return Direction.NW;
		} else {
			return Direction.NE;
		}
	}

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
