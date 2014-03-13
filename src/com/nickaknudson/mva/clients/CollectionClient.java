/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> model type backing collection
 *
 */
public interface CollectionClient<M extends Model<M>> extends Client {
	/**
	 * @return type token
	 */
	abstract TypeToken<Collection<M>> getCollectionTypeToken();
}
