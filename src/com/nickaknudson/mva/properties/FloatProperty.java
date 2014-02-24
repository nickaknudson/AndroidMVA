/**
 * 
 */
package com.nickaknudson.mva.properties;

/**
 * @author nick
 *
 */
public class FloatProperty extends Property<Float> {
	
	/**
	 * @param afloat
	 */
	public FloatProperty(Float afloat) {
		setObject(afloat);
	}
	
	/**
	 * @param string
	 */
	public FloatProperty(String string) {
		if(string != null) {
			Float afloat = Float.parseFloat(string);
			setObject(afloat);
		}
	}

	@Override
	public Float getDefault() {
		return (float) 0.0;
	}

}
