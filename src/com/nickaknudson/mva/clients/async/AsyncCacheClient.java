package com.nickaknudson.mva.clients.async;

import java.io.File;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CacheCallback;
import com.nickaknudson.mva.clients.CacheClient;

/**
 * @author nick
 * @param <M> model type
 */
public abstract class AsyncCacheClient<M extends Model<M>> implements CacheClient<M> {

	/**
	 * @param url
	 * @param filename
	 * @param callback
	 */
	public void cache(String url, String filename, final CacheCallback callback) {
		AsyncHttpClient.getDefaultInstance().getFile(url, filename, new AsyncHttpClient.FileCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
				if(e != null) {
					if(callback != null) callback.onError(e);
				} else {
					if(callback != null) callback.onCache(result);
				}
			}
		});
	}
	
	// TODO implement cache
}
