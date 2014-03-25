package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.ModelObserver;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class ModelViewAdapter<M extends Model<M>> extends ViewAdapter implements ModelAdapter<M> {
	protected static final String TAG = ModelViewAdapter.class.getSimpleName();

	private M model;
	
	/**
	 * @param activity
	 * @param root
	 * @param model
	 */
	public ModelViewAdapter(Activity activity, ViewGroup root, M model) {
		super(activity, root);
		setModel(model);
	}
	
	/**
	 * @param activity
	 * @param convertView
	 * @param model
	 */
	public ModelViewAdapter(Activity activity, View convertView, M model) {
		super(activity, convertView);
		setModel(model);
	}

	/**
	 * @return model
	 */
	public M getModel() {
		return model;
	}
	
	/**
	 * Call {@link #refresh} on this adapter after setting a new model
	 * @param m
	 */
	public void setModel(M m) {
		// remove old reference
		if(model != null) model.remove(objectObserver);
		// add observer and set object
		model = m;
		if(model != null) model.add(objectObserver);
	}
	
	private ModelObserver<M> objectObserver = new ModelObserver<M>(){
		@Override
		public void onChange(M model, Object data) {
			onModelChange(model, data);
			refresh();
		}
	};

	/**
	 * This method is provided for convenience.
	 * {@link #refresh()} will be called for you automatically.
	 * This is called before refresh().
	 * 
	 * @param model
	 * @param data
	 */
	protected abstract void onModelChange(M model, Object data);

	@Override
	protected View fillView(View view) {
		return fillView(view, model);
	}
	
	abstract protected View fillView(View view, M model);
}
