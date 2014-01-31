/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> 
 *
 */
public interface CollectionClient<T extends Model<T>> extends Client {
	/**
	 * @return
	 */
	abstract TypeToken<Collection<T>> getCollectionTypeToken();
}
