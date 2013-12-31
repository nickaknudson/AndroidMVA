package com.nickaknudson.mva.clients.async;

import java.lang.reflect.Type;
import java.net.URI;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.IndexCallback;
import com.nickaknudson.mva.clients.IndexClient;

/**
 * @author nick
 * @param <T> model type for collection
 */
public abstract class AndroidAsyncIndexClient<T extends Model<T>> implements IndexClient<T> {
	
	private Gson gson = new Gson();

	protected void index(String url, final Collection<T> collection, final IndexCallback<T> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONArray(req, new AsyncHttpClient.JSONArrayCallback() {

			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONArray result) {
				if(e == null && result != null) {
					String json = result.toString();
					Collection<T> rcollection = gson.fromJson(json, getType());
					collection.addAll(rcollection);
					callback.onIndex(collection);
				} else {
					callback.onError(e);
				}
				
			}
		});
	}

	@Override
	public Type getType() {
		return getTypeToken().getType();
	}
}
