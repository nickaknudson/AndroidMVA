package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.callbacks.PersistentCallback;

/**
 * @author nick
 *
 */
public interface PersistentClient extends Client {	
	/**
	 * @param callback
	 */
	public abstract void connect(final PersistentCallback callback);
	/**
	 * @param callback
	 * @return
	 */
	public abstract boolean add(PersistentCallback callback);
	/**
	 * @param callback
	 * @return
	 */
	public abstract boolean remove(PersistentCallback callback);
	/**
	 * 
	 */
	public abstract void disconnect();
	/**
	 * @return
	 */
	public abstract boolean isConnected();
}
