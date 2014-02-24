/**
 * 
 */
package com.nickaknudson.mva.properties;

/**
 * @author nick
 *
 */
public class IntegerProperty extends Property<Integer> {
	
	/**
	 * @param integer
	 */
	public IntegerProperty(Integer integer) {
		setObject(integer);
	}
	
	/**
	 * @param string
	 */
	public IntegerProperty(String string) {
		if(string != null) {
			Integer integer = Integer.parseInt(string);
			setObject(integer);
		}
	}

	@Override
	public Integer getDefault() {
		return 0;
	}
}
