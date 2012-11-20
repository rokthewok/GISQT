package gissystem.exceptions;

import gissystem.models.helpers.BeanFacade;

/**
 * Thrown when setting a property in a Bean is not successful.
 * @see BeanFacade
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
