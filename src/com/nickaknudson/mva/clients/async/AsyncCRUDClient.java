package com.nickaknudson.mva.clients.async;

import java.lang.reflect.Type;
import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DestroyCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;
import com.nickaknudson.mva.clients.CRUDClient;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class AsyncCRUDClient<M extends Model<M>> implements CRUDClient<M> {
	
	private Gson gson = new Gson();

	protected void create(String url, final M model, final CreateCallback<M> callback) {
		try {
			URI uri = URI.create(url);
			AsyncHttpRequest req = new AsyncHttpRequest(uri, "POST");
			JSONObject json = new JSONObject(gson.toJson(model, getType()));
			req.setBody(new JSONObjectBody(json));
			AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {
				@Override
				public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
					if(e == null && result != null) {
						String json = result.toString();
						M rmodel = gson.fromJson(json, getType());
						model.set(rmodel);
						callback.onCreate(model);
					} else {
						callback.onError(e);
					}
				}
			});
		} catch (JSONException e) {
			callback.onError(e);
		}
	}

	protected void read(String url, final M model, final ReadCallback<M> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
				if(e == null && result != null) {
					String json = result.toString();
					M rmodel = gson.fromJson(json, getType());
					model.set(rmodel);
					callback.onRead(model);
				} else {
					callback.onError(e);
				}
			}
		});
	}

	protected void update(String url, final M model, final UpdateCallback<M> callback) {
		try {
			URI uri = URI.create(url);
			AsyncHttpRequest req = new AsyncHttpRequest(uri, "PUT");
			JSONObject json = new JSONObject(gson.toJson(model, getType()));
			req.setBody(new JSONObjectBody(json));
			AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {
				@Override
				public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
					if(e == null && result != null) {
						String json = result.toString();
						M rmodel = gson.fromJson(json, getType());
						model.set(rmodel);
						callback.onUpdate(model);
					} else {
						callback.onError(e);
					}
				}
			});
		} catch (JSONException e) {
			callback.onError(e);
		}
	}

	protected void destroy(String url, final M model, final DestroyCallback<M> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "DELETE");
		AsyncHttpClient.getDefaultInstance().execute(req, new HttpConnectCallback() {
			@Override
			public void onConnectCompleted(Exception e, AsyncHttpResponse response) {
				if(e == null) {
					//model.set(rmodel);
					callback.onDestroy(model);
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
