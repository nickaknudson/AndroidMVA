/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous update operation
 * 
 * @author nick
 * @param <M> model type
 *
 */
public interface UpdateCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous update operation completed
	 * @param model
	 */
	public void onUpdate(M model);
}
