package com.nickaknudson.mva.clients;

import java.util.concurrent.Future;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;

public abstract class CRUDClient<T extends Model> implements Client {
	protected static final String TAG = CRUDClient.class.getSimpleName();
	
	public abstract Future<T> create(T model);
	public abstract Future<T> read(T model);
	public abstract Future<T> update(T model);
	public abstract Future<Boolean> destroy(T model);
}
