/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous receive operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface ReceiveCallback<T extends Model<T>> extends Callback {
	/**
	 * Asynchronous receive operation completed
	 * @param model
	 */
	public void onReceive(T model);
}
