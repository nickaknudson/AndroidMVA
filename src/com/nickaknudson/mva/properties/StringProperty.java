/**
 * 
 */
package com.nickaknudson.mva.properties;

/**
 * @author nick
 * @param <M> 
 *
 */
public class StringProperty extends Property<String> {
	
	/**
	 * @param string
	 */
	public StringProperty(String string) {
		setObject(string);
	}
	
	/**
	 * @param integer
	 */
	public StringProperty(Integer integer) {
		if(integer != null) {
			setObject(integer.toString());
		}
	}

	@Override
	public String getDefault() {
		return "";
	}
}
