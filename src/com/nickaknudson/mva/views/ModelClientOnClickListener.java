package com.nickaknudson.mva.views;

import android.view.View;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.Client;

public abstract class ModelClientOnClickListener<T extends Model, C extends Client> extends ModelOnClickListener<T> {

	private C client;

	public ModelClientOnClickListener(T model, C client) {
		super(model);
		this.client = client;
	}

	@Override
	public void onClick(View v, T model) {
		onClick(v, model, client);
	}
	
	public abstract void onClick(View v, T model, C client);
}
