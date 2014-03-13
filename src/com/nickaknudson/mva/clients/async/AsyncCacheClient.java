package com.nickaknudson.mva.clients.async;

import java.io.File;
import java.security.InvalidParameterException;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.callbacks.CacheCallback;
import com.nickaknudson.mva.clients.CacheClient;

/**
 * @author nick
 */
public abstract class AsyncCacheClient implements CacheClient {

	/**
	 * @param url
	 * @param filename
	 * @param callback
	 */
	public void cache(String url, String filename, final CacheCallback callback) {
		if(callback == null) throw new InvalidParameterException("callback cannot be null");
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
}
