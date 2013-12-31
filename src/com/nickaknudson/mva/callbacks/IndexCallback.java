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
 * @param <T> model type of collection
 *
 */
public interface IndexCallback<T extends Model<T>> extends ErrorCallback {
	/**
	 * Asynchronous index operation completed
	 * @param collection
	 */
	public void onIndex(Collection<T> collection);
}
