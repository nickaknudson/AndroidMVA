/**
 * 
 */
package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface CollectionClient<T extends Model<T>> extends Client {
	abstract Type getType();
	abstract TypeToken<Collection<T>> getTypeToken();
}
