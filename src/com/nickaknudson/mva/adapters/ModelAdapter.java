package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> model type
 */
public interface ModelAdapter<T extends Model<T>> {
	/**
	 * @return model
	 */
	public T getModel();
	/**
	 * @param model
	 */
	public void setModel(T model);
}
