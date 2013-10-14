package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.clients.Client;

public abstract class ModelClientAdapter<T extends Model<T>, C extends Client> extends ModelAdapter<T> {

	private C client;

	public ModelClientAdapter(Activity activity, View convertView, T model, C client) {
		super(activity, convertView, model);
		setClient(client);
	}

	public ModelClientAdapter(Activity activity, ViewGroup root, boolean attachToRoot, T model, C client) {
		super(activity, root, attachToRoot, model);
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
	protected View fillView(View view, T model) {
		return fillView(view, model, client);
	}
	
	protected abstract View fillView(View view, T model, C client);

}
