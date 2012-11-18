package gissystem.models;

import java.util.Formatter;

/**
 * Represents a single geographic coordinate. A Java Bean (POD).
 * @author John Ruffer
 *
 */
public class GeographicCoordinate {
	private int degrees;
	private int minutes;
	private int seconds;
	private double decimal;
	private String cardinalDirection;
	
	public GeographicCoordinate( int degrees, int minutes, int seconds, String cardinalDirection, double decimal ) {
		this.degrees = degrees;
		this.minutes = minutes;
		this.seconds = seconds;
		this.decimal = decimal;
		this.cardinalDirection = cardinalDirection;
	}
	
	public int getDegrees() {
		return this.degrees;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
	
	public int getSeconds() {
		return this.seconds;
	}
	
	public double getDecimalValue() {
		return this.decimal;
	}
	
	public int toSeconds() {
		int totalSeconds = this.degrees * 3600 + this.minutes * 60 + this.seconds;
		if( this.cardinalDirection == "South" || this.cardinalDirection == "West" ) {
			totalSeconds = -totalSeconds;
		}
		
		return totalSeconds;
	}
	
	public String getCardinalDirection() {
		return this.cardinalDirection;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter( sb );
		formatter.format( "%03d%02d%02d", this.degrees, this.minutes, this.seconds );
		sb.append( this.cardinalDirection );
		sb.append( " " );
		sb.append( this.decimal );
		formatter.close();
		
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
		
		if( !( aThat instanceof GeographicCoordinate ) ) {
			return false;
		}
		
		GeographicCoordinate that = (GeographicCoordinate) aThat;
		return this.degrees == that.degrees &&
			   this.minutes == that.minutes &&
			   this.seconds == that.seconds &&
			   this.decimal == that.decimal &&
			   this.cardinalDirection.equals( that.cardinalDirection );
	}
}
