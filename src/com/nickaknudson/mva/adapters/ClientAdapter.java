package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.clients.Client;

public abstract class ClientAdapter<C extends Client> extends Adapter {

	private C client;

	public ClientAdapter(Activity activity, View convertView, C client) {
		super(activity, convertView);
		setClient(client);
	}

	public ClientAdapter(Activity activity, ViewGroup root, boolean attachToRoot, C client) {
		super(activity, root, attachToRoot);
		setClient(client);
	}
	
	protected C getClient() {
		return client;
	}

	private void setClient(C c) {
		client = c;
		refresh();
	}

	@Override
	protected View fillView(View view) {
		return fillView(view, client);
	}
	
	protected abstract View fillView(View view, C client);
}
