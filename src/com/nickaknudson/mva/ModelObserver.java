package com.nickaknudson.mva;

/**
 * @author nick
 *
 * @param <T>
 */
public interface ModelObserver<T extends Model<T>> {
	/**
	 * @param model
	 */
	public void onChange(Model<T> model);
}
