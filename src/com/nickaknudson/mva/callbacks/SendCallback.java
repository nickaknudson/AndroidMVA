/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous send operation
 * @author nick
 * @param <M> 
 *
 */
public interface SendCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous send operation completed
	 * @param model
	 */
	public void onSend(M model);
}
