/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;


/**
 * @author nick
 *
 */
public interface SetCallback<T extends Model<T>> extends Callback {
	public void onSet(T model);
}
