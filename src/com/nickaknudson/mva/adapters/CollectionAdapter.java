package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> model type of collection
 */
public interface CollectionAdapter<T extends Model<T>> {
	/**
	 * @return collection
	 */
	public Collection<T> getCollection();
	/**
	 * @param collection
	 */
	public void setCollection(Collection<T> collection);
}
