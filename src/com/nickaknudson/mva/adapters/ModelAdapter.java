package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.Observer;

public abstract class ModelAdapter<T extends Model<T>> extends Adapter {
	protected static final String TAG = Adapter.class.getSimpleName();

	private T model;
	
	public ModelAdapter(Activity activity, ViewGroup root, boolean attachToRoot, T model) {
		super(activity, root, attachToRoot);
		setModel(model);
	}
	
	public ModelAdapter(Activity activity, View convertView, T model) {
		super(activity, convertView);
		setModel(model);
	}

	public T getModel() {
		return model;
	}
	
	public void setModel(T m) {
		// remove old reference
		if(model != null) model.deleteObserver(objectObserver);
		// add observer and set object
		model = m;
		if(model != null) model.addObserver(objectObserver);
		refresh();
	}
	
	private Observer<T> objectObserver = new Observer<T>(){
		@Override
		public void update(T object, Object data) {
			onUpdate((T) object, data);
		}
	};
	
	protected void onUpdate(T model, Object data) {
		refresh();
	}

	@Override
	protected View fillView(View view) {
		return fillView(view, model);
	}
	
	abstract protected View fillView(View view, T model);
}
