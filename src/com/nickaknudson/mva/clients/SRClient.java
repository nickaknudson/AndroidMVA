package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.ReceiveCallback;

public interface SRClient<T extends Model<T>> extends ModelClient<T> {
	public abstract void send(T model);
	public abstract void receive(final ReceiveCallback<T> callback);
	public abstract boolean addReceiveCallback(ReceiveCallback<T> callback);
	public abstract boolean removeReceiveCallback(ReceiveCallback<T> callback);
}
