/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous read operation
 * 
 * @author nick
 * @param <M> model type
 *
 */
public interface ReadCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous read operation completed
	 * @param model
	 */
	public void onRead(M model);
}
