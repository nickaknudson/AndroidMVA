package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.IndexCallback;

/**
 * @author nick
 *
 * @param <M>
 */
public interface IndexClient<M extends Model<M>> extends CollectionClient<M> {
	/**
	 * @param collection
	 * @param callback
	 */
	public void index(Collection<M> collection, IndexCallback<M> callback);
}
