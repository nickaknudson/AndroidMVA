package com.nickaknudson.mva.callbacks;

/**
 * @author nick
 *
 */
public interface PersistentCallback extends ErrorCallback {
	/**
	 * Asynchronous connection operation completed
	 */
	public void onConnected();
	/**
	 * Asynchronous disconnection operation completed
	 */
	public void onDisconnected();
}
