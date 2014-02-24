/**
 * 
 */
package com.nickaknudson.mva.properties;

/**
 * @author nick
 *
 */
public class BooleanProperty extends Property<Boolean> {
	
	/**
	 * @param aboolean
	 */
	public BooleanProperty(Boolean aboolean) {
		setObject(aboolean);
	}
	
	/**
	 * @param string
	 */
	public BooleanProperty(String string) {
		if (string != null && string.contains("1")) {
			setObject(true);
		} else if (string != null && string.contains("0")) {
			setObject(false);
		}
	}

	@Override
	public Boolean getDefault() {
		return false;
	}
}
