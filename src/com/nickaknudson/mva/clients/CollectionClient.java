/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> 
 *
 */
public interface CollectionClient<M extends Model<M>> extends Client {
	/**
	 * @return
	 */
	abstract TypeToken<Collection<M>> getCollectionTypeToken();
}
