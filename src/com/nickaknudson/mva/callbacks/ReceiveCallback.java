/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous receive operation
 * 
 * @author nick
 * @param <M> model type
 *
 */
public interface ReceiveCallback<M extends Model<M>> extends ManagedCallback {
	/**
	 * Asynchronous receive operation completed
	 * @param model
	 */
	public void onReceive(M model);
}
