/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * Asynchronous index operation
 * 
 * @author nick
 * @param <M> model type of collection
 *
 */
public interface IndexCallback<M extends Model<M>> extends ErrorCallback {
	/**
	 * Asynchronous index operation completed
	 * @param collection
	 */
	public void onIndex(Collection<M> collection);
}
