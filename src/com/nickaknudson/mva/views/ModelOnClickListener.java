package com.nickaknudson.mva.views;

import com.nickaknudson.mva.Model;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class ModelOnClickListener<T extends Model> implements OnClickListener {

	private T model;

	public ModelOnClickListener(T model) {
		this.model = model;
	}
	
	@Override
	public void onClick(View v) {
		onClick(v, model);
	}
	
	public abstract void onClick(View v, T model);
}
