package com.nickaknudson.mva;

/**
 * @author nick
 *
 * @param <M> model type
 */
public interface ModelObserver<M extends Model<M>> {
	/**
	 * @param model
	 * @param data 
	 */
	public void onChange(M model, Object data);
}
