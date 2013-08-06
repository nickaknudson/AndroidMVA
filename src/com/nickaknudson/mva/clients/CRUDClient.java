package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DeleteCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;

public interface CRUDClient<T extends Model<T>> extends Client {
	public abstract void create(T model, CreateCallback<T> callback);
	public abstract void read(T model, ReadCallback<T> callback);
	public abstract void update(T model, UpdateCallback<T> callback);
	public abstract void destroy(T model, DeleteCallback callback);
}
