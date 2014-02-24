/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> 
 */
public interface SelectCallback<M extends Model<M>> {
	/**
	 * Asynchronous select operation completed
	 * @param model
	 */
	public void onSelect(M model);
}
