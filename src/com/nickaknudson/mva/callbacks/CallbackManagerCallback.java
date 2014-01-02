package com.nickaknudson.mva.callbacks;


/**
 * @author nick
 *
 * @param <C> callback type
 */
public interface CallbackManagerCallback<C extends Callback> extends Callback {
	/**
	 * @param callback
	 */
	public void onCallback(C callback);
}
