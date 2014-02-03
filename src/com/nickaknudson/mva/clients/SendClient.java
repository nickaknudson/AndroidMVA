package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.SendCallback;

/**
 * @author nick
 *
 * @param <M>
 */
public interface SendClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param model
	 * @param callback
	 */
	public void send(M model, SendCallback<M> callback);
}
