/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous create operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface CreateCallback<T extends Model<T>> extends ErrorCallback {
	/**
	 * Asynchronous create operation completed
	 * @param model
	 */
	public void onCreate(T model);
}
