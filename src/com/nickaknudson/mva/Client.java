package com.nickaknudson.mva;

import java.util.concurrent.Future;

import android.content.Context;

public abstract class Client<T extends Model> {
	protected static final String TAG = Client.class.getSimpleName();

	private Context context;

	public Client(Context context) {
		setContext(context);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
