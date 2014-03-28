package com.nickaknudson.mva.clients.async;

import java.lang.reflect.Type;
import java.net.URI;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.FetchCallback;
import com.nickaknudson.mva.clients.FetchClient;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class AsyncFetchClient<M extends Model<M>> implements FetchClient<M> {
	
	private Gson gson = new Gson();

	protected void fetch(String url, final M model, final FetchCallback<M> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {

			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
				if(e == null && result != null) {
					String json = result.toString();
					M rmodel = gson.fromJson(json, getType());
					model.set(rmodel);
					callback.onFetch(model);
				} else {
					callback.onError(e);
				}
			}
		});
	}

	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}
}
