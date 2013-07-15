package com.nickaknudson.android;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CollectionAdapter<T> extends BaseAdapter {
	protected static final String TAG = CollectionAdapter.class.getSimpleName();

	private Collection<T> collection;
	private Activity activity;
	
	public CollectionAdapter(Activity activity, Collection<T> collection) {
		setActivity(activity);
		setCollection(collection);
	}
	
	public void setActivity(Activity a) {
		activity = a;
	}
	
	public void setCollection(Collection<T> c) {
		// remove old reference
		if(collection != null) collection.deleteObserver(collectionObserver);
		// add observer and set list
		collection = c;
		if(collection != null) collection.addObserver(collectionObserver);
		notifyDataSetChangedTS();
	}
	
	@Override
	public int getCount() {
		return collection != null ? collection.size() : 0;
	}
	
	public Collection<T> getCollection() {
		return collection;
	}

	@Override
	public T getItem(int position) {
		return collection.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int pos, View cV, ViewGroup r) {
		T item = getItem(pos);
		return getView(activity, item, cV, r);
	}
	
	public abstract View getView(Activity activity, T item, View convertView, ViewGroup root);
	
	protected void onUpdate(Collection<T> observable, Object data) {
		notifyDataSetChangedTS();
	}
	
	private Observer collectionObserver = new Observer(){
		@Override
		@SuppressWarnings("unchecked")
		public void update(Observable observable, Object data) {
			onUpdate((Collection<T>) observable, data);
		}
	};
	
	/*
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

	/*
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
