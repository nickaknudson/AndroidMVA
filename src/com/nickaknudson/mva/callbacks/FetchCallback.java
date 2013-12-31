/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous fetch operation
 * 
 * @author nick
 * @param <T> model type
 *
 */
public interface FetchCallback<T extends Model<T>> extends ErrorCallback {
	/**
	 * Asynchronous fetch operation completed
	 * @param model
	 */
	public void onFetch(T model);
}
