package com.nickaknudson.android;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class Controller<T extends Model> {
	protected static final String TAG = Controller.class.getSimpleName();

	private T model;
	private View view;
	private Activity activity;
	
	public Controller(Activity activity, T model, ViewGroup root, boolean attachToRoot) {
		setActivity(activity);
		generateViewTS(activity.getLayoutInflater(), root, attachToRoot);
		setModel(model);
	}
	
	public Controller(Activity activity, T model, View convertView) {
		setActivity(activity);
		view = convertView;
		setModel(model);
	}

	public T getObject() {
		return model;
	}

	public View getView() {
		return view;
	}
	
	public void setActivity(Activity a) {
		activity = a;
	}
	
	public void setModel(T m) {
		// remove old reference
		if(model != null) model.deleteObserver(objectObserver);
		// add observer and set object
		model = m;
		if(model != null) model.addObserver(objectObserver);
		fillViewTS(model);
	}

	public void setView(View view) {
		this.view = view;
		// if the passed a new convertable view, fill it
		fillViewTS(model);
	}
	
	protected void onUpdate(T model, Object data) {
		fillViewTS(model);
	}
	
	private Observer objectObserver = new Observer(){
		@Override
		@SuppressWarnings("unchecked")
		public void update(Observable object, Object data) {
			onUpdate((T) object, data);
		}
	};
	
	/*
	 * Thread Safe Method - Generate View
	 * generate a view if we don't have a convertView
	 */
	abstract protected View generateView(LayoutInflater layoutInflater, ViewGroup root, boolean attachToRoot);
	
	protected void generateViewTS(LayoutInflater i, ViewGroup p, boolean a) {
		activity.runOnUiThread(new GenerateViewRunnable(i, p, a));
	}
	
	protected class GenerateViewRunnable implements Runnable {
		
		private LayoutInflater layoutInflater;
		private ViewGroup root;
		private boolean attachToRoot;
		
		public GenerateViewRunnable(LayoutInflater i, ViewGroup r, boolean a) {
			layoutInflater = i;
			root = r;
			attachToRoot = a;
		}
		
		@Override
		public void run() {
			view = generateView(layoutInflater, root, attachToRoot);
		}
	}

	/*
	 * Thread Safe Method - Fill View
	 * here is where we do everything important
	 */
	abstract protected View fillView(T model);
	
	protected void fillViewTS(T m) {
		activity.runOnUiThread(new FillViewRunnable(m));
	}
	
	protected class FillViewRunnable implements Runnable {
		
		private T model;
		
		public FillViewRunnable(T m) {
			model = m;
		}
		
		@Override
		public void run() {
			fillView(model);
		}
	}
}

