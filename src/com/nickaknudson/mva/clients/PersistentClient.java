package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;

public interface PersistentClient<T extends Model> extends Client {	
	public abstract void connect(final PersistentClientCallback callback);
	public abstract boolean removePersistentClientCallback(PersistentClientCallback callback);
	public abstract void disconnect();
	public abstract boolean isConnected();
}
