package gissystem.models;

import gissystem.helpers.Direction;
import gissystem.interfaces.IPoint;

public class Index implements IPoint {
	private long x;
	private long y;
	
	public Index( long x, long y ) {
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
}
