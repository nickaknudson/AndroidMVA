/**
 * 
 */
package com.nickaknudson.mva.clients;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> 
 *
 */
public interface ModelClient<M extends Model<M>> extends Client {
	/**
	 * @return type token
	 */
	abstract TypeToken<M> getTypeToken();
}
