/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.ReceiveCallback;

/**
 * @author nick
 *
 */
public interface ReceiveClient<T extends Model<T>> extends ModelClient<T> {
	public abstract void receive(final ReceiveCallback<T> callback);
	public abstract boolean add(ReceiveCallback<T> callback);
	public abstract boolean remove(ReceiveCallback<T> callback);
}
