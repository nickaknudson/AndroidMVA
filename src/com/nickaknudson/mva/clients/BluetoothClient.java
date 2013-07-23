package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;

import android.bluetooth.BluetoothDevice;
import com.google.gson.Gson;
import com.nickaknudson.android.bluetooth.BluetoothConnection;
import com.nickaknudson.android.bluetooth.BluetoothConnectionCallback;
import com.nickaknudson.mva.Model;

public abstract class BluetoothClient<T extends Model> implements SRClient<T>, PersistentClient<T> {

	private BluetoothDevice device;
	private boolean secure;
	private BluetoothConnection bluetoothConnection;
	protected boolean connected = false;
	private Gson gson;
	private PersistentClientCallbackManager pccallbacks;
	private SRClientCallbackManager<T> srcallbacks;

	public BluetoothClient(BluetoothDevice device, boolean secure) {
		pccallbacks = new PersistentClientCallbackManager();
		srcallbacks = new SRClientCallbackManager<T>();
		bluetoothConnection = new BluetoothConnection();
		this.device = device;
		this.secure = secure;
		gson = new Gson();
	}
	
	public void start() { // TODO
		bluetoothConnection.start(bluetoothConnectionCallback);
	}

	@Override
	public void connect(final PersistentClientCallback callback) {
		pccallbacks.add(callback);
		start();
		if(device != null) {
			bluetoothConnection.connect(device, secure, bluetoothConnectionCallback);
		}
	}
	
	@Override
	public boolean removePersistentClientCallback(PersistentClientCallback callback) {
		return pccallbacks.remove(callback);
	}

	@Override
	public void disconnect() {
		bluetoothConnection.stop();
		connected = false;
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void send(T model) {
		String message = gson.toJson(model, getType());
		byte[] out = message.getBytes();
		bluetoothConnection.write(out);
	}

	@Override
	public void recieve(final SRClientCallback<T> callback) {
		srcallbacks.add(callback);
	}
	
	@Override
	public boolean removeSRClientCallback(SRClientCallback<T> callback) {
		return srcallbacks.remove(callback);
	}
	
	private BluetoothConnectionCallback bluetoothConnectionCallback = new BluetoothConnectionCallback() {

		@Override
		public void onConnection(BluetoothConnection connection) {
			connected = true;
			pccallbacks.onConnected(null);
		}

		@Override
		public void onFailed(BluetoothConnection connection) {
			connected = false;
			pccallbacks.onDisconnected(null); // TODO
		}

		@Override
		public void onLost(BluetoothConnection connection) {
			connected = false;
			pccallbacks.onDisconnected(null); // TODO
		}

		@Override
		public void onRecieve(int bytes, byte[] buffer) {
			try {
				if(bytes > 0 && buffer != null && buffer.length >= 1) {
					String message = new String(buffer, 0 , bytes);
					T model = gson.fromJson(message, getType());
					srcallbacks.onRecieve(model);
				}
			} catch(ClassCastException e) {
				// TODO
			} catch(Exception e) {
				// TODO
			}
		}
    };
    
    public abstract Type getType();
}
