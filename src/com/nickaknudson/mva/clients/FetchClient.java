package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.FetchCallback;

/**
 * @author nick
 *
 * @param <M>
 */
public interface FetchClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param model
	 * @param callback
	 */
	public void fetch(M model, FetchCallback<M> callback);
}
