/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.ReceiveCallback;

/**
 * @author nick
 * @param <M> 
 *
 */
public interface ReceiveClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param callback
	 */
	public abstract void receive(final ReceiveCallback<M> callback);
	/**
	 * Add a callback to the client
	 * @param callback
	 * @return if the callback was successfully added
	 */
	public abstract boolean add(ReceiveCallback<M> callback);
	/**
	 * Remove a callback from the client
	 * @param callback
	 * @return if the callback was successfully removed
	 */
	public abstract boolean remove(ReceiveCallback<M> callback);
}
