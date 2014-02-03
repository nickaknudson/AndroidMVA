/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;


/**
 * Asynchronous set operation
 * 
 * @author nick
 * @param <M> model type
 *
 */
public interface SetCallback<M extends Model<M>> extends Callback {
	/**
	 * Asynchronous set operation completed
	 * @param model
	 */
	public void onSet(M model);
}
