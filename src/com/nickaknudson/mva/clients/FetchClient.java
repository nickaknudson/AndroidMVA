package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.FetchCallback;

public interface FetchClient<T extends Model<T>> extends Client {
	public void fetch(T model, FetchCallback<T> callback);
}
