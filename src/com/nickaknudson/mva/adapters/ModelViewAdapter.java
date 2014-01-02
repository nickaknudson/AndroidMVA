package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.Observer;

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
