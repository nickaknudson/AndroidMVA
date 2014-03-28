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
 * @param <M> model type for collection
 */
public abstract class AsyncIndexClient<M extends Model<M>> implements IndexClient<M> {
	
	private Gson gson = new Gson();

	protected void index(String url, final Collection<M> collection, final IndexCallback<M> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONArray(req, new AsyncHttpClient.JSONArrayCallback() {

			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONArray result) {
				if(e == null && result != null) {
					String json = result.toString();
					Collection<M> rcollection = gson.fromJson(json, getCollectionType());
					collection.addAll(rcollection);
					callback.onIndex(collection);
				} else {
					callback.onError(e);
				}
				
			}
		});
	}

	/**
	 * @return collection type
	 */
	public Type getCollectionType() {
		return getCollectionTypeToken().getType();
	}
}
