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
	 * @param callback
	 * @return
	 */
	public abstract boolean add(ReceiveCallback<M> callback);
	/**
	 * @param callback
	 * @return
	 */
	public abstract boolean remove(ReceiveCallback<M> callback);
}
