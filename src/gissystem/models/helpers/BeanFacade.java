package gissystem.models.helpers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import gissystem.exceptions.SetPropertyException;

/**
 * A facade/proxy class to simplify Bean reflection tools.
 * @author John Ruffer
 *
 * @param <T> The Bean class type.
 */
public class BeanFacade<T> {
	private T beanObject;
	private BeanInfo beanInfo;

	/**
	 * Ctor.
	 * @param beanObject The bean that will be modified through the Facade.
	 * @throws IntrospectionException If getting the bean info causes an issue.
	 */
	public BeanFacade( T beanObject ) throws IntrospectionException {
		this.beanObject = beanObject;
		this.beanInfo = Introspector.getBeanInfo( beanObject.getClass() );
	}
	
	/**
	 * Setter for a bean property.
	 * @param property The programmatic name of the property to be set.
	 * @param value The value to be assigned to the property.
	 * @throws SetPropertyException If there is an issue setting the property.
	 */
	public void setProperty( String property, Object value ) throws SetPropertyException {
		PropertyDescriptor [] descriptors = this.beanInfo.getPropertyDescriptors();
		
		PropertyDescriptor descriptor = null;
		for( PropertyDescriptor d : descriptors ) {
			if( d.getName().equals( property ) ) {
				descriptor = d;
			}
		}
		
		if( descriptor == null ) {
			throw new SetPropertyException();
		}
		
		try {
			descriptor.getWriteMethod().invoke( beanObject, value );
		} catch( IllegalAccessException e ) {
			throw new SetPropertyException( e.toString() );
		} catch( InvocationTargetException e ) {
			throw new SetPropertyException( e.toString() );
		}
	}
	
	/**
	 * Get the programmatic property names of the bean.
	 * @return A list of property names.
	 */
	public List<String> getPropertyNames() {
		PropertyDescriptor [] descriptors = this.beanInfo.getPropertyDescriptors();
		List<String> descriptorNames = new ArrayList<String>();
		
		for( PropertyDescriptor descriptor : descriptors ) {
			descriptorNames.add( descriptor.getName() );
		}
		
		return descriptorNames;
	}
	
	/**
	 * Get the bean object stored by the BeanFacade object.
	 * @return The bean object.
	 */
	public T getBeanObject() {
		return this.beanObject;
	}
}

