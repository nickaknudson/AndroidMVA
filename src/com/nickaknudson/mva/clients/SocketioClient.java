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
import com.nickaknudson.mva.callbacks.PersistentCallback;
import com.nickaknudson.mva.callbacks.PersistentCallbackManager;
import com.nickaknudson.mva.callbacks.ReceiveCallback;
import com.nickaknudson.mva.callbacks.ReceiveCallbackManager;

public abstract class SocketioClient<T extends Model<T>> implements SRClient<T>, PersistentClient {

	protected SocketIOClient socketIOClient;
	private String uri;
	private Gson gson;
	private String name;
	private PersistentCallbackManager pcallbacks;
	private ReceiveCallbackManager<T> rcallbacks;

	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}
	
	public SocketioClient(String uri, String name) {
		pcallbacks = new PersistentCallbackManager();
		rcallbacks = new ReceiveCallbackManager<T>();
		this.name = name; // TODO
		this.uri = uri;
		gson = new Gson();
	}

	@Override
	public void connect(final PersistentCallback callback) {
		addConnectCallback(callback);
        SocketIORequest req = new SocketIORequest(uri);
        req.setLogging("Socket.IO", Log.VERBOSE);
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), req, new ConnectCallback() {
        	
    		@Override
    		public void onConnectCompleted(Exception ex, SocketIOClient client) {
    			socketIOClient = client;
    			socketIOClient.setDisconnectCallback(new DisconnectCallback() {

					@Override
					public void onDisconnect(Exception e) {
						pcallbacks.onDisconnected(e);
					}
    			});
    			pcallbacks.onConnected(ex);
    		}
        });
	}
	
	@Override
	public boolean addConnectCallback(PersistentCallback callback) {
		return pcallbacks.add(callback);
	}
	
	@Override
	public boolean removeConnectCallback(PersistentCallback callback) {
		return pcallbacks.remove(callback);
	}

	@Override
	public void disconnect() {
		if(socketIOClient != null) {
			socketIOClient.disconnect();
		} else {
			// TODO
		}
	}

	@Override
	public boolean isConnected() {
		if(socketIOClient != null) {
			return socketIOClient.isConnected();
		} else {
			return false;
		}
	}
	
	public void send(T model) { // TODO name
		if(socketIOClient != null) {
			try {
				String json = gson.toJson(model, getType());
				JSONArray args = new JSONArray("[" + json + "]");
				socketIOClient.emit(name, args, new Acknowledge() {
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
	public void receive(final ReceiveCallback<T> callback) { // TODO name
		addReceiveCallback(callback);
		if(socketIOClient != null) {
			socketIOClient.on(name, new EventCallback() {
				
				@Override
				public void onEvent(JSONArray argument, Acknowledge acknowledge) {
					try {
						if(argument != null && argument.length() >= 1) {
							JSONObject obj = argument.getJSONObject(0);
							String json = obj.toString();
							T model = gson.fromJson(json, getType());
							rcallbacks.onReceive(model);
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
	public boolean addReceiveCallback(ReceiveCallback<T> callback) {
		return rcallbacks.add(callback);
	}
	
	@Override
	public boolean removeReceiveCallback(ReceiveCallback<T> callback) {
		return rcallbacks.remove(callback);
	}
	
	public abstract Type getType();
}
