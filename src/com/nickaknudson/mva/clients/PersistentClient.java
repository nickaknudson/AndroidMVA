package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.callbacks.PersistentCallback;

/**
 * @author nick
 *
 */
public interface PersistentClient extends Client {	
	/**
	 * Instruct the client to establish a connection
	 * @param callback
	 */
	public abstract void connect(final PersistentCallback callback);
	/**
	 * Instruct the client to disconnect
	 */
	public abstract void disconnect();
	/**
	 * Add a callback to the client
	 * @param callback
	 * @return if the callback was successfully added
	 */
	public abstract boolean add(PersistentCallback callback);
	/**
	 * Remove a callback from the client
	 * @param callback
	 * @return if the callback was successfully removed
	 */
	public abstract boolean remove(PersistentCallback callback);
	/**
	 * @return if the client is in a connected state
	 */
	public abstract boolean isConnected();
}
