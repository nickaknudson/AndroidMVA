package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.callbacks.ViewCallback;
import com.nickaknudson.mva.callbacks.ViewCallbackManager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author nick
 *
 */
public abstract class ViewAdapter implements Adapter {
	protected static final String TAG = ViewAdapter.class.getSimpleName();

	private View view;
	private Activity activity;
	private ViewCallbackManager gcallbacks = new ViewCallbackManager();
	private ViewCallbackManager fcallbacks = new ViewCallbackManager();
	
	/**
	 * @param activity
	 * @param root
	 * @param attachToRoot
	 */
	public ViewAdapter(Activity activity, ViewGroup root) {
		setActivity(activity);
		generateViewTS(activity.getLayoutInflater(), root);
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
	 * @return view
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
		// if passed a new convertable view, fill it
		refresh();
	}
	
	/**
	 * Refresh the view by calling it's {@link #fillView} method
	 */
	public void refresh() {
		fillViewTS();
	}
	
	/**
	 * Thread Safe Method - Generate View
	 * generate a view if we don't have a convertView
	 */
	abstract protected View generateView(LayoutInflater layoutInflater, ViewGroup root);
	
	protected void generateViewTS(final LayoutInflater layoutInflater, final ViewGroup root) {
		activity.runOnUiThread(new GenerateViewRunnable(layoutInflater, root));
	}
	
	private class GenerateViewRunnable implements Runnable {
		
		private LayoutInflater layoutInflater;
		private ViewGroup root;
		
		public GenerateViewRunnable(LayoutInflater i, ViewGroup r) {
			layoutInflater = i;
			root = r;
		}
		
		@Override
		public void run() {
			View v = generateView(layoutInflater, root);
			setView(v);
			gcallbacks.onView(v);
		}
	}
	
	/**
	 * Alerts observers that the view has been generated
	 * @param replay
	 * @param callback
	 * @return added
	 */
	public boolean onGenerateView(boolean replay, ViewCallback callback) {
		return gcallbacks.add(replay, callback);
	}
	
	/**
	 * Alerts observers that the view has been generated
	 * @param replay
	 * @param callback
	 * @return added
	 */
	public boolean onGenerateViewOnce(boolean replay, ViewCallback callback) {
		return gcallbacks.once(replay, callback);
	}

	/**
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
		Thread thread = new Thread() {
			public void run() {
				activity.runOnUiThread(new FillViewRunnable());
			}
		};
		thread.start();
	}
	
	protected class FillViewRunnable implements Runnable {
		
		@Override
		public void run() {
			fillView();
			fcallbacks.onView(view);
		}
	}
	
	/**
	 * Alerts observers that the view has been filled
	 * @param replay
	 * @param callback
	 * @return added
	 */
	public boolean onFillView(boolean replay, ViewCallback callback) {
		return fcallbacks.add(replay, callback);
	}
	
	/**
	 * Alerts observers that the view has been filled
	 * @param replay
	 * @param callback
	 * @return added
	 */
	public boolean onFillViewOnce(boolean replay, ViewCallback callback) {
		return fcallbacks.once(replay, callback);
	}
}

