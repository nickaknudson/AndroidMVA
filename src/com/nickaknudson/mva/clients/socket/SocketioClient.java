package com.nickaknudson.mva.clients.socket;

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
import com.nickaknudson.mva.clients.PersistentClient;
import com.nickaknudson.mva.clients.SRClient;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class SocketioClient<M extends Model<M>> implements SRClient<M>, PersistentClient {

	protected SocketIOClient socketIOClient;
	private Gson gson = new Gson();
	private PersistentCallbackManager pcallbacks = new PersistentCallbackManager();
	private ReceiveCallbackManager<M> rcallbacks = new ReceiveCallbackManager<M>();

	/**
	 * @return the underlying socket.io client
	 */
	public SocketIOClient getSocketIOClient() {
		return socketIOClient;
	}

	/**
	 * @param uri
	 * @param callback
	 */
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
	
	/**
	 * @param name
	 * @param model
	 * @param callback
	 */
	public void send(String name, final M model, final SendCallback<M> callback) { // TODO name
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

	/**
	 * @param name
	 * @param callback
	 */
	public void receive(String name, final ReceiveCallback<M> callback) { // TODO name
		add(callback);
		if(socketIOClient != null) {
			socketIOClient.on(name, new EventCallback() {
				
				@Override
				public void onEvent(JSONArray argument, Acknowledge acknowledge) {
					try {
						if(argument != null && argument.length() >= 1) {
							JSONObject obj = argument.getJSONObject(0);
							String json = obj.toString();
							M model = gson.fromJson(json, getType());
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
	public boolean add(ReceiveCallback<M> callback) {
		return rcallbacks.add(callback);
	}
	
	@Override
	public boolean remove(ReceiveCallback<M> callback) {
		return rcallbacks.remove(callback);
	}
	
	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}
}
