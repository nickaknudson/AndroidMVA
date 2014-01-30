/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> 
 *
 */
public interface ModelClient<T extends Model<T>> extends Client {
	/**
	 * @return type token
	 */
	abstract TypeToken<T> getTypeToken();
}
