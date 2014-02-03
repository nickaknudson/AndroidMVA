package com.nickaknudson.mva.views;

import com.nickaknudson.mva.Model;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class ModelOnClickListener<M extends Model<M>> implements OnClickListener {

	private M model;

	/**
	 * @param model
	 */
	public ModelOnClickListener(M model) {
		this.model = model;
	}
	
	@Override
	public void onClick(View v) {
		onClick(v, model);
	}
	
	/**
	 * @param v
	 * @param model
	 */
	public abstract void onClick(View v, M model);
}
