package com.nickaknudson.mva.clients.async;

import java.io.File;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.FetchCallback;
import com.nickaknudson.mva.clients.FetchClient;

/**
 * @author nick
 * @param <M> 
 *
 */
public abstract class AsyncFetchFileClient<M extends Model<M>> implements FetchClient<M> {
	
	/**
	 * @param url
	 * @param filename
	 * @param callback
	 */
	protected void fetch(String url, String filename, final FetchCallback<File> callback) {
		AsyncHttpClient.getDefaultInstance().getFile(url, filename, new AsyncHttpClient.FileCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
				if(e != null) {
					if(callback != null) callback.onError(e);
				} else {
					if(callback != null) callback.onFetch(result);
				}
			}
		});
	}
}
