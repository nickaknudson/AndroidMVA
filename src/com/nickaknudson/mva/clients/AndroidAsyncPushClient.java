package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;
import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.JSONObjectBody;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.PushCallback;

public abstract class AndroidAsyncPushClient<T extends Model<T>> implements PushClient<T> {
	
	private Gson gson = new Gson();

	protected void push(String url, final T model, final PushCallback<T> callback) {
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
						callback.onPush(model);
					} else {
						callback.onError(e);
					}
				}
			});
		} catch(JSONException e) {
			callback.onError(e);
		}
	}

	@Override
	public Type getType() {
		return getTypeToken().getType();
	}
}
