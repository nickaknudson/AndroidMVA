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
import com.nickaknudson.mva.callbacks.SendCallback;

public abstract class SocketioClient<T extends Model<T>> implements SRClient<T>, PersistentClient {

	protected SocketIOClient socketIOClient;
	private Gson gson = new Gson();
	private PersistentCallbackManager pcallbacks = new PersistentCallbackManager();
	private ReceiveCallbackManager<T> rcallbacks = new ReceiveCallbackManager<T>();

	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}

	public void connect(String uri, final PersistentCallback callback) {
		add(callback);
        SocketIORequest req = new SocketIORequest(uri);
        req.setLogging("Socket.IO", Log.VERBOSE);
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), req, new ConnectCallback() {
        	
    		@Override
    		public void onConnectCompleted(Exception ex, SocketIOClient client) {
    			if(ex != null) {
    				pcallbacks.onError(ex);
    			} else {
	    			socketIOClient = client;
	    			socketIOClient.setDisconnectCallback(new DisconnectCallback() {
	
						@Override
						public void onDisconnect(Exception e) {
							if(e != null) {
								pcallbacks.onError(e);
							} else {
								pcallbacks.onDisconnected();
							}
						}
	    			});
	    			pcallbacks.onConnected();
    			}
    		}
        });
	}
	
	@Override
	public boolean add(PersistentCallback callback) {
		return pcallbacks.add(callback);
	}
	
	@Override
	public boolean remove(PersistentCallback callback) {
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
	
	public void send(String name, final T model, final SendCallback<T> callback) { // TODO name
		if(socketIOClient != null) {
			try {
				String json = gson.toJson(model, getType());
				JSONArray args = new JSONArray("[" + json + "]");
				socketIOClient.emit(name, args, new Acknowledge() {
					@Override
					public void acknowledge(JSONArray arguments) {
						// TODO parse arguments returned by acknowledge
						if(callback != null) callback.onSend(model);
					}
				});
			} catch (JSONException e) {
				if(callback != null) callback.onError(e);
			}
		}
	}

	public void receive(String name, final ReceiveCallback<T> callback) { // TODO name
		add(callback);
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
	public boolean add(ReceiveCallback<T> callback) {
		return rcallbacks.add(callback);
	}
	
	@Override
	public boolean remove(ReceiveCallback<T> callback) {
		return rcallbacks.remove(callback);
	}

	@Override
	public Type getType() {
		return getTypeToken().getType();
	}
}
