/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;


/**
 * Asynchronous set operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface SetCallback<T extends Model<T>> extends Callback {
	/**
	 * Asynchronous set operation completed
	 * @param model
	 */
	public void onSet(T model);
}
