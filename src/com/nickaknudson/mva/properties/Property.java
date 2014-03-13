/**
 * 
 */
package com.nickaknudson.mva.properties;

/**
 * @author nick
 * @param <T> 
 */
public abstract class Property<T> {
	
	private T object;
	
	/**
	 * @return object
	 */
	public T get() {
		if(object != null) {
			return object;
		} else {
			return getDefault();
		}
	}
	
	/**
	 * @return default
	 */
	public abstract T getDefault();
	
	protected T getObject() {
		return object;
	}
	
	/**
	 * @param object
	 */
	protected void setObject(T object) {
		this.object = object;
	}
}
