package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.callbacks.CacheCallback;

/**
 * @author nick
 *
 */
public interface CacheClient extends Client {
	/**
	 * @param callback
	 */
	public abstract void cache(final CacheCallback callback);

}
