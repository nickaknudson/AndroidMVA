/**
 * 
 */
package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.ModelObserver;

/**
 * @author nick
 * @param <M>
 */
public abstract class ModelAdapterBase<M extends Model<M>> implements ModelAdapter<M> {

	private M model;
	
	/**
	 * @param model
	 */
	public ModelAdapterBase(M model) {
		setModel(model);
	}

	@Override
	public M getModel() {
		return model;
	}

	@Override
	public void setModel(M model) {
		if(this.model != null) model.remove(observer);
		this.model = model;
		if(this.model != null) model.add(observer);
	}
	
	private ModelObserver<M> observer = new ModelObserver<M>() {
		@Override
		public void onChange(M model, Object data) {
			onModelChange(model, data);
		}
	};

	/**
	 * @param model
	 * @param data
	 */
	protected abstract void onModelChange(M model, Object data);
}
