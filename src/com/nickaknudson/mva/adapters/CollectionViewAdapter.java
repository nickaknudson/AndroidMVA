package com.nickaknudson.mva.adapters;

import java.security.InvalidParameterException;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.CollectionObserver;
import com.nickaknudson.mva.Model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;

/**
 * @author nick
 *
 * @param <M> model type of collection
 */
public abstract class CollectionViewAdapter<M extends Model<M>> extends BaseAdapter implements CollectionAdapter<M> {
	protected static final String TAG = CollectionViewAdapter.class.getSimpleName();

	private Collection<M> collection;
	private Activity activity;
	@SuppressWarnings("rawtypes")
	private AdapterView adapterView;
	
	/**
	 * @param activity
	 * @param collection
	 */
	public CollectionViewAdapter(Activity activity, Collection<M> collection) {
		setActivity(activity);
		setCollection(collection);
	}
	
	/**
	 * @param a
	 */
	public void setActivity(Activity a) {
		activity = a;
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
			notifyDataSetChangedTS();
		}
	};
	
	/**
	 * This binds the adapter to an AdapterView
	 * @param view
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void bindTo(AdapterView view) {
		// TODO perhaps have this adapter create it's adapter view and auto bind
		// just like view adapter generates it's own view
		if(adapterView != null) {
			throw new InvalidParameterException("This adapter had already been bound to a view");
		}
		adapterView = view;
		adapterView.setAdapter(this);
		// set on click listener
		adapterView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				M item = getItem(position);
				onItemClicked(adapterView, view, item, id);
			}
		});
		// set on long click listener
		adapterView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
				M item = getItem(position);
				return onItemLongClicked(adapterView, view, item, id);
			}
		});
		// set on selected listener
		adapterView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				M item = getItem(position);
				onItemSelect(adapterView, view, item, id);
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				onNothingSelect(adapterView);
			}
		});
	}
	
	/**
	 * @param adapterView 
	 * @param view
	 * @param item 
	 * @param id
	 */
	public abstract void onItemClicked(AdapterView<?> adapterView, View view, M item, long id);
	
	/**
	 * @param adapterView 
	 * @param view
	 * @param item 
	 * @param id
	 * @return true if the callback consumed the long click, false otherwise 
	 */
	public abstract boolean onItemLongClicked(AdapterView<?> adapterView, View view, M item, long id);
	
	/**
	 * @param adapterView 
	 * @param view
	 * @param item 
	 * @param id
	 */
	public abstract void onItemSelect(AdapterView<?> adapterView, View view, M item, long id);
	
	/**
	 * @param adapterView
	 */
	public abstract void onNothingSelect(AdapterView<?> adapterView);
	
	/**
	 * @return adapter view
	 */
	@SuppressWarnings("rawtypes")
	public AdapterView getAdapterView() {
		return adapterView;
	}
	
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
