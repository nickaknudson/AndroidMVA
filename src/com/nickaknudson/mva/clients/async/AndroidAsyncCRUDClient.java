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
import com.nickaknudson.mva.callbacks.DeleteCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;
import com.nickaknudson.mva.clients.CRUDClient;

/**
 * @author nick
 *
 * @param <T>
 */
public abstract class AndroidAsyncCRUDClient<T extends Model<T>> implements CRUDClient<T> {
	
	private Gson gson = new Gson();

	protected void create(String url, final T model, final CreateCallback<T> callback) {
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
						T rmodel = gson.fromJson(json, getType());
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

	protected void read(String url, final T model, final ReadCallback<T> callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "GET");
		AsyncHttpClient.getDefaultInstance().executeJSONObject(req, new AsyncHttpClient.JSONObjectCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
				if(e == null && result != null) {
					String json = result.toString();
					T rmodel = gson.fromJson(json, getType());
					model.set(rmodel);
					callback.onRead(model);
				} else {
					callback.onError(e);
				}
			}
		});
	}

	protected void update(String url, final T model, final UpdateCallback<T> callback) {
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
						T rmodel = gson.fromJson(json, getType());
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

	protected void delete(String url, final T model, final DeleteCallback callback) {
		URI uri = URI.create(url);
		AsyncHttpRequest req = new AsyncHttpRequest(uri, "DELETE");
		AsyncHttpClient.getDefaultInstance().execute(req, new HttpConnectCallback() {
			@Override
			public void onConnectCompleted(Exception e, AsyncHttpResponse response) {
				if(e == null) {
					//model.set(rmodel);
					callback.onDelete();
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
