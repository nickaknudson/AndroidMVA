package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.ModelObserver;

/**
 * @author nick
 *
 * @param <T>
 */
public abstract class ModelViewAdapter<T extends Model<T>> extends ViewAdapter implements ModelAdapter<T> {
	protected static final String TAG = ModelViewAdapter.class.getSimpleName();

	private T model;
	
	/**
	 * @param activity
	 * @param root
	 * @param model
	 */
	public ModelViewAdapter(Activity activity, ViewGroup root, T model) {
		super(activity, root);
		setModel(model);
	}
	
	/**
	 * @param activity
	 * @param convertView
	 * @param model
	 */
	public ModelViewAdapter(Activity activity, View convertView, T model) {
		super(activity, convertView);
		setModel(model);
	}

	/**
	 * @return model
	 */
	public T getModel() {
		return model;
	}
	
	/**
	 * @param m
	 */
	public void setModel(T m) {
		// remove old reference
		if(model != null) model.remove(objectObserver);
		// add observer and set object
		model = m;
		if(model != null) model.add(objectObserver);
		refresh();
	}
	
	private ModelObserver<T> objectObserver = new ModelObserver<T>(){
		@Override
		public void onChange(Model<T> model, Object data) {
			refresh();
		}
	};

	@Override
	protected View fillView(View view) {
		return fillView(view, model);
	}
	
	abstract protected View fillView(View view, T model);
}
