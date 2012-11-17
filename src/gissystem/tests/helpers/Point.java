package gissystem.tests.helpers;

import gissystem.helpers.Direction;
import gissystem.interfaces.IPoint;

public class Point implements IPoint {
	
	private long x;
	private long y;
	
	public Point( long x, long y ) {
		this.x = x;
		this.y = y;
	}

	@Override
	public long getX() {
		return this.x;
	}

	@Override
	public long getY() {
		return this.y;
	}

	@Override
	public Direction directionFrom( long X, long Y ) {
		if( this.x == X && this.y == Y ) {
			return Direction.NOQUADRANT;
		} else if( this.x < X && this.y <= Y ) {
			return Direction.SW;
		} else if( this.x >= X && this.y < Y ) {
			return Direction.SE;
		} else if( this.x <= X && this.y > Y ) {
			return Direction.NW;
		} else {
			return Direction.NE;
		}
	}

	@Override
	public Direction inQuadrant( double xLo, double xHi, double yLo, double yHi ) {
		if( this.x > xHi || this.x < xLo || this.y > yHi || this.y < yLo ) {
			return Direction.NOQUADRANT;
		} else if( this.x < ( xHi - xLo ) / 2.0 && this.y <= ( yHi - yLo ) / 2.0 ) {
			return Direction.SW;
		} else if( this.x >= ( xHi - xLo ) / 2.0 && this.y < (yHi - yLo ) / 2.0 ) {
			return Direction.SE;
		} else if( this.x <= ( xHi - xLo ) / 2.0 && this.y > ( yHi - yLo) / 2.0 ) {
			return Direction.NW;
		} else {
			return Direction.NE;
		}
	}

	@Override
	public boolean inBox( double xLo, double xHi, double yLo, double yHi ) {
		return ( this.x > xLo && this.x < xHi ) && 
				( this.y > yLo && this.y < yHi );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Point: " );
		sb.append( this.x );
		sb.append( ", " );
		sb.append( this.y );
		return sb.toString();
	}
	
	@Override
	public boolean equals( Object other ) {
		if( this == other ) {
			return true;
		}
		
		if( !( other instanceof Point ) ) {
			return false;
		}
		
		Point that = (Point) other;
		
		return this.x == that.x && this.y == that.y; 
	}
}
