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
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.SendCallback;
import com.nickaknudson.mva.clients.SendClient;

/**
 * @author nick
 *
 * @param <T>
 */
public abstract class AndroidAsyncSendClient<T extends Model<T>> implements SendClient<T> {
	
	private Gson gson = new Gson();

	protected void send(String url, final T model, final SendCallback<T> callback) {
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
						if(callback != null) callback.onSend(model);
					} else {
						if(callback != null) callback.onError(e);
					}
				}
			});
		} catch(JSONException e) {
			if(callback != null) callback.onError(e);
		}
	}

	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}
}
