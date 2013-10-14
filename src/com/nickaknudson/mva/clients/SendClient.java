package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.SendCallback;

public interface SendClient<T extends Model<T>> extends ModelClient<T> {
	public void send(T model, SendCallback<T> callback);
}
