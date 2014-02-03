/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous create operation
 * 
 * @author nick
 * @param <M> model type
 *
 */
public interface CreateCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous create operation completed
	 * @param model
	 */
	public void onCreate(M model);
}
