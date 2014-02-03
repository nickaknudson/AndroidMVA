/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * Asynchronous destroy operation
 * 
 * @author nick
 * @param <M> 
 *
 */
public interface DestroyCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous destroy operation completed
	 * @param model 
	 */
	public void onDestroy(M model);
}
