package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.SendCallback;

/**
 * @author nick
 *
 * @param <T>
 */
public interface SendClient<T extends Model<T>> extends ModelClient<T> {
	/**
	 * @param model
	 * @param callback
	 */
	public void send(T model, SendCallback<T> callback);
}
