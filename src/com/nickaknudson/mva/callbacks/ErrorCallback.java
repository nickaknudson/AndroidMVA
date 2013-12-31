/**
 * 
 */
package com.nickaknudson.mva.callbacks;

/**
 * An asynchronous operation that may return an error
 * 
 * @author nick
 * 
 */
public interface ErrorCallback extends Callback {
	/**
	 * Asynchronous operation returned an error
	 * @param e Exception
	 */
	public void onError(Exception e);
}
