package com.nickaknudson.mva.clients;

import java.util.concurrent.Future;

import android.content.Context;

import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;

public abstract class CRUDClient<T extends Model> extends Client<T> {


	public CRUDClient(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public abstract Future<T> create(T model);
	public abstract Future<T> read(T model);
	public abstract Future<T> update(T model);
	public abstract Future<Boolean> destroy(T model);
}
