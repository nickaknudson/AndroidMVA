/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous fetch operation
 * 
 * @author nicMk
 * @param <M> object type
 *
 */
public interface FetchCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous fetch operation completed
	 * @param model
	 */
	public void onFetch(M model);
}
