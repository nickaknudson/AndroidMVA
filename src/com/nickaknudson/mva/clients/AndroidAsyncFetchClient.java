package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;
import java.net.URI;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.FetchCallback;

public abstract class AndroidAsyncFetchClient<T extends Model<T>> implements FetchClient<T> {
	
	private Gson gson = new Gson();

	protected void fetch(String url, final T model, final FetchCallback<T> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {

			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
				if(e == null && result != null) {
					String json = result.toString();
					T rmodel = gson.fromJson(json, getType());
					model.set(rmodel);
					callback.onFetch(model);
				} else {
					callback.onError(e);
				}
			}
		});
	}

	protected abstract Type getType();
}
