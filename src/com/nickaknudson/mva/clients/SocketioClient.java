package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.DisconnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.SocketIORequest;
import com.nickaknudson.mva.Model;

public abstract class SocketioClient<T extends Model> implements SRClient<T>, PersistentClient<T> {

	protected SocketIOClient socketIOClient;
	private String uri;
	private Gson gson;
	private String name;
	private PersistentClientCallbackManager pccallbacks;
	private SRClientCallbackManager<T> srcallbacks;

	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}
	
	public SocketioClient(String uri, String name) {
		pccallbacks = new PersistentClientCallbackManager();
		srcallbacks = new SRClientCallbackManager<T>();
		this.name = name;
		this.uri = uri;
		gson = new Gson();
	}

	@Override
	public void connect(final PersistentClientCallback callback) {
		pccallbacks.add(callback);
        SocketIORequest req = new SocketIORequest(uri);
        req.setLogging("Socket.IO", Log.VERBOSE);
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), req, new ConnectCallback() {
        	
    		@Override
    		public void onConnectCompleted(Exception ex, SocketIOClient client) {
    			socketIOClient = client;
    			socketIOClient.setDisconnectCallback(new DisconnectCallback() {

					@Override
					public void onDisconnect(Exception e) {
						pccallbacks.onDisconnected(e);
					}
    			});
    			pccallbacks.onConnected(ex);
    		}
        });
	}
	
	@Override
	public boolean removePersistentClientCallback(PersistentClientCallback callback) {
		return pccallbacks.remove(callback);
	}

	@Override
	public void disconnect() {
		if(getSocketIOClient() != null) {
			getSocketIOClient().disconnect();
		} else {
			// TODO
		}
	}

	@Override
	public boolean isConnected() {
		if(getSocketIOClient() != null) {
			return getSocketIOClient().isConnected();
		} else {
			return false;
		}
	}
	
	public void send(T model) {
		if(getSocketIOClient() != null) {
			try {
				String json = gson.toJson(model, getType());
				JSONArray args = new JSONArray("[" + json + "]");
				getSocketIOClient().emit(name, args, new Acknowledge() {
					@Override
					public void acknowledge(JSONArray arguments) {
						// TODO Auto-generated method stub
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void recieve(final SRClientCallback<T> callback) {
		srcallbacks.add(callback);
		if(getSocketIOClient() != null) {
			getSocketIOClient().on(name, new EventCallback() {
				
				@Override
				public void onEvent(JSONArray argument, Acknowledge acknowledge) {
					try {
						if(argument != null && argument.length() >= 1) {
							JSONObject obj = argument.getJSONObject(0);
							String json = obj.toString();
							T model = gson.fromJson(json, getType());
							srcallbacks.onRecieve(model);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassCastException e) {
						//TODO
					} catch (Exception e) {
						//TODO
					}
				}
			});
		}
	}
	
	@Override
	public boolean removeSRClientCallback(SRClientCallback<T> callback) {
		return srcallbacks.remove(callback);
	}
	
	public abstract Type getType();
}
