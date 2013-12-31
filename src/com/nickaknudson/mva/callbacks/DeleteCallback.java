/**
 * 
 */
package com.nickaknudson.mva.callbacks;

/**
 * Asynchronous delete operation
 * 
 * @author nick
 *
 */
public interface DeleteCallback extends ErrorCallback {
	/**
	 * Asynchronous delete operation completed
	 */
	public void onDelete();
}
