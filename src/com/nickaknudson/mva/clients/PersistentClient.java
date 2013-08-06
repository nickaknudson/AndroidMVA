package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.callbacks.PersistentCallback;

public interface PersistentClient extends Client {	
	public abstract void connect(final PersistentCallback callback);
	public abstract boolean addConnectCallback(PersistentCallback callback);
	public abstract boolean removeConnectCallback(PersistentCallback callback);
	public abstract void disconnect();
	public abstract boolean isConnected();
}
