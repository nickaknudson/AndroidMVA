package com.nickaknudson.mva.callbacks;


/**
 * @author nick
 *
 * @param <C> callback type
 */
public interface CallbackManagerTrigger<C extends Callback> {
	/**
	 * @param callback
	 */
	public void triggerCallback(C callback);
}
