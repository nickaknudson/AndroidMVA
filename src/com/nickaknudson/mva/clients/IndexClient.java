package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.IndexCallback;

/**
 * @author nick
 *
 * @param <T>
 */
public interface IndexClient<T extends Model<T>> extends CollectionClient<T> {
	/**
	 * @param collection
	 * @param callback
	 */
	public void index(Collection<T> collection, IndexCallback<T> callback);
}
