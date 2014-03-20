package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.CollectionObserver;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.ViewCallback;
import com.nickaknudson.mva.callbacks.ViewCallbackManager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author nick
 *
 * @param <M> model type of collection
 */
public abstract class CollectionViewAdapter<M extends Model<M>> extends BaseAdapter implements CollectionAdapter<M> {
	protected static final String TAG = CollectionViewAdapter.class.getSimpleName();

	private View view;
	private Activity activity;
	private Collection<M> collection;
	private ViewCallbackManager gcallbacks = new ViewCallbackManager();
	private ViewCallbackManager fcallbacks = new ViewCallbackManager();
	
	/**
	 * @param activity
	 * @param root
	 * @param collection
	 */
	public CollectionViewAdapter(Activity activity, ViewGroup root, Collection<M> collection) {
		setActivity(activity);
		generateViewTS(activity.getLayoutInflater(), root);
		setCollection(collection);
	}
	
	/**
	 * @param activity
	 * @param convertView
	 * @param collection
	 */
	public CollectionViewAdapter(Activity activity, View convertView, Collection<M> collection) {
		setActivity(activity);
		setView(convertView);
		setCollection(collection);
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
	public boolean onGenerateView(ViewCallback callback) {
		return gcallbacks.add(callback);
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
	public boolean onFillView(ViewCallback callback) {
		return fcallbacks.add(callback);
	}
	
	/**
	 * Call {@link #refresh} on this adapter after setting a new collection
	 * @param c
	 */
	public void setCollection(Collection<M> c) {
		// remove old reference
		if(collection != null) collection.remove(collectionObserver);
		// add observer and set list
		collection = c;
		if(collection != null) collection.add(collectionObserver);
	}
	
	private CollectionObserver<M> collectionObserver = new CollectionObserver<M>(){
		
		@Override
		public void onChange(Collection<M> collection, Object data) {
			onCollectionChange(collection, data);
			notifyDataSetChangedTS();
		}
	};
	
	protected abstract void onCollectionChange(Collection<M> collection, Object data);
	
	@Override
	public int getCount() {
		return collection != null ? collection.size() : 0;
	}

	public Collection<M> getCollection() {
		return collection;
	}

	@Override
	public M getItem(int position) {
		return collection.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int pos, View cV, ViewGroup r) {
		M item = getItem(pos);
		if(cV != null) {
			return getView(activity, cV, item);
		} else {
			return getView(activity, r, item);
		}
	}
	
	/**
	 * @param activity
	 * @param convertView
	 * @param model
	 * @return view
	 */
	public abstract View getView(Activity activity, View convertView, M model);
	
	/**
	 * @param activity
	 * @param root
	 * @param model
	 * @return view
	 */
	public abstract View getView(Activity activity, ViewGroup root, M model);
	
	/**
	 * Refresh the view by calling it's {@link #fillView} method
	 */
	public void refresh() {
		notifyDataSetChangedTS();
	}
	
	/**
	 * Thread Safe Method - NotifyDataSetChanged
	 */
	protected void notifyDataSetChangedTS() {
		activity.runOnUiThread(new NotifyDataSetChangedRunnable());
	}
	
	protected class NotifyDataSetChangedRunnable implements Runnable {
		
		@Override
		public void run() {
			notifyDataSetChanged();
		}
	}

	/**
	 * Thread Safe Method - NotifyDataSetInvalidated
	 */
	protected void notifyDataSetInvalidatedTS() {
		activity.runOnUiThread(new NotifyDataSetInvalidatedRunnable());
	}
	
	protected class NotifyDataSetInvalidatedRunnable implements Runnable {
		
		@Override
		public void run() {
			notifyDataSetInvalidated();
		}
	}
}
