/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous update operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface UpdateCallback<T extends Model<T>> extends ErrorCallback {
	/**
	 * Asynchronous update operation completed
	 * @param model
	 */
	public void onUpdate(T model);
}
