package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DeleteCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;

public interface CRUDClient<T extends Model<T>> extends ModelClient<T> {
	public abstract void create(final T model, final CreateCallback<T> callback);
	public abstract void read(final T model, final ReadCallback<T> callback);
	public abstract void update(final T model, final UpdateCallback<T> callback);
	public abstract void destroy(final T model, final DeleteCallback callback);
}
