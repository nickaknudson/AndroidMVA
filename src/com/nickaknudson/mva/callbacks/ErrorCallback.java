/**
 * 
 */
package com.nickaknudson.mva.callbacks;


/**
 * @author nick
 *
 */
public interface ErrorCallback extends Callback {
	public void onError(Exception e);
}
