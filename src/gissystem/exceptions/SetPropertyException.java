package gissystem.exceptions;

/**
 * Thrown when setting a property in a Bean is not successful.
 * @author John Ruffer
 *
 */
@SuppressWarnings("serial")
public class SetPropertyException extends Exception {
	
	public SetPropertyException() {
		this( "" );
	}
	
	public SetPropertyException( String message ) {
		super( "An exception has been raised in Bean Setter's setProperty method" +
				": " + message );
	}
}
