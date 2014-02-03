package com.nickaknudson.mva.adapters;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * A CollectionAdapter provides the set of logic associated with a
 * {@link com.nickaknudson.mva.Collection}. The CollectionAdapter then becomes
 * the primary interface with which to manipulate models within a collection
 * and receive lifecylce callbacks related to those models.
 * @author nick
 * @param <M> model type of collection
 */
public interface CollectionAdapter<M extends Model<M>> {
	/**
	 * @return collection
	 */
	public Collection<M> getCollection();
	/**
	 * @param collection
	 */
	public void setCollection(Collection<M> collection);
}
