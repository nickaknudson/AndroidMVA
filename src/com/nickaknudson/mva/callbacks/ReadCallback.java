/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous read operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface ReadCallback<T extends Model<T>> extends ErrorCallback {
	/**
	 * Asynchronous read operation completed
	 * @param model
	 */
	public void onRead(T model);
}
