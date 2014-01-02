package com.nickaknudson.mva.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * An adapter defines a logical set of business logic. The primary use case
 * being an intermediary between a {@link com.nickaknudson.mva.Model} and a
 * View. While this is the primary usage, an Adapter does not need to present a
 * UI or 
 * 
 * @author nick
 *
 */
public abstract class ViewAdapter {
	protected static final String TAG = ViewAdapter.class.getSimpleName();

	private View view;
	private Activity activity;
	
	/**
	 * @param activity
	 * @param root
	 * @param attachToRoot
	 */
	public ViewAdapter(Activity activity, ViewGroup root, boolean attachToRoot) {
		setActivity(activity);
		generateViewTS(activity.getLayoutInflater(), root, attachToRoot);
	}
	
	/**
	 * @param activity
	 * @param convertView
	 */
	public ViewAdapter(Activity activity, View convertView) {
		setActivity(activity);
		setView(convertView);
	}
	
	protected Activity getActivity() {
		return activity;
	}

	/**
	 * @return
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * @param a
	 */
	public void setActivity(Activity a) {
		activity = a;
	}

	/**
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
		// if the passed a new convertable view, fill it
		refresh();
	}
	
	protected void refresh() {
		fillViewTS();
	}
	
	/*
	 * Thread Safe Method - Generate View
	 * generate a view if we don't have a convertView
	 */
	abstract protected View generateView(LayoutInflater layoutInflater, ViewGroup root, boolean attachToRoot);
	
	protected void generateViewTS(LayoutInflater i, ViewGroup p, boolean a) {
		activity.runOnUiThread(new GenerateViewRunnable(i, p, a));
	}
	
	private class GenerateViewRunnable implements Runnable {
		
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
			View v = generateView(layoutInflater, root, attachToRoot);
			setView(v);
		}
	}

	/*
	 * Thread Safe Method - Fill View
	 * here is where we do everything important
	 */
	protected View fillView() {
		synchronized(view) {
			return fillView(view);
		}
	}
	
	abstract protected View fillView(View view);
	
	private void fillViewTS() {
		activity.runOnUiThread(new FillViewRunnable());
	}
	
	protected class FillViewRunnable implements Runnable {
		
		@Override
		public void run() {
			fillView();
		}
	}
}

