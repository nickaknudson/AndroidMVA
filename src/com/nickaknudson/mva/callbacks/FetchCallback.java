/**
 * 
 */
package com.nickaknudson.mva.callbacks;

/**
 * Asynchronous fetch operation
 * 
 * @author nick
 * @param <T> object type
 *
 */
public interface FetchCallback<T> extends ErrorCallback {
	/**
	 * Asynchronous fetch operation completed
	 * @param object
	 */
	public void onFetch(T object);
}
