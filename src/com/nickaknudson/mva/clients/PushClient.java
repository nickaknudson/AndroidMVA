package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.PushCallback;

public interface PushClient<T extends Model<T>> extends ModelClient<T> {
	public void push(T model, PushCallback<T> callback);
}
