package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CacheCallback;

/**
 * @author nick
 * 
 * @param <M> model type
 */
public interface CacheClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param model 
	 * @param callback
	 */
	public abstract void cache(final M model, final CacheCallback callback);
}
