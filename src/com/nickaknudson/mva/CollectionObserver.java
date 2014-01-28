package com.nickaknudson.mva;

/**
 * @author nick
 *
 * @param <M> model type of collection
 */
public interface CollectionObserver<M extends Model<M>> {
	/**
	 * @param collection 
	 * @param data 
	 */
	public void onChange(Collection<M> collection, Object data);
}
