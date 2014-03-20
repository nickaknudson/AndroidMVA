package com.nickaknudson.mva.adapters.collection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.adapters.CollectionViewAdapter;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class CollectionGridViewAdapter<M extends Model<M>> extends CollectionViewAdapter<M> {

	/**
	 * @param activity
	 * @param convertView
	 * @param collection
	 */
	public CollectionGridViewAdapter(Activity activity, View convertView, Collection<M> collection) {
		super(activity, convertView, collection);
	}

	/**
	 * @param activity
	 * @param root
	 * @param collection
	 */
	public CollectionGridViewAdapter(Activity activity, ViewGroup root, Collection<M> collection) {
		super(activity, root, collection);
	}

	@Override
	protected View generateView(LayoutInflater layoutInflater, ViewGroup root) {
		GridView gridView = new GridView(getActivity().getBaseContext());
		return gridView;
	}

	@Override
	protected View fillView(View view) {
		GridView adapterView = (GridView) view;
		adapterView.setAdapter(this);
		adapterView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View itemView, int position, long id) {
				M model = getItem(position);
				CollectionGridViewAdapter.this.onItemClick(adapterView, itemView, model, position, id);
			}
		});
		adapterView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View itemView, int position, long id) {
				M model = getItem(position);
				return CollectionGridViewAdapter.this.onItemLongClick(adapterView, itemView, model, position, id);
			}
		});
		adapterView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View itemView, int position, long id) {
				M model = getItem(position);
				CollectionGridViewAdapter.this.onItemSelected(adapterView, itemView, model, position, id);
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				CollectionGridViewAdapter.this.onNothingSelected(adapterView);
			}
		});
		return adapterView;
	}

	protected abstract void onItemClick(AdapterView<?> adapterView, View itemView,
			M model, int position, long id);

	protected abstract boolean onItemLongClick(AdapterView<?> adapterView,
			View itemView, M model, int position, long id);

	protected abstract void onItemSelected(AdapterView<?> adapterView, View itemView,
			M model, int position, long id);

	protected abstract void onNothingSelected(AdapterView<?> adapterView);
}
