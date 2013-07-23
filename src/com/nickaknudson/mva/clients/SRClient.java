package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;

public interface SRClient<T extends Model> extends Client {
	public abstract void send(T model);
	public abstract void recieve(final SRClientCallback<T> callback);
	public abstract boolean removeSRClientCallback(SRClientCallback<T> callback);
}
