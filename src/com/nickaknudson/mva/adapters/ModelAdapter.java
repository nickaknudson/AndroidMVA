package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Model;

/**
 * A ModelAdapter provides the set of logic associated with a
 * {@link com.nickaknudson.mva.Model}. The ModelAdapter then becomes
 * the primary interface with which to manipulate a model and receive
 * lifecylce callbacks related to that model.
 * @author nick
 * @param <M> model type
 */
public interface ModelAdapter<M extends Model<M>> {
	/**
	 * @return model
	 */
	public M getModel();
	/**
	 * @param model
	 */
	public void setModel(M model);
}
