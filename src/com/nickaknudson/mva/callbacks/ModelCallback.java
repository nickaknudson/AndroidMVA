package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T>
 */
public interface ModelCallback<T extends Model<T>> extends Callback {
	/**
	 * @param model
	 */
	public void onModel(T model);
}
