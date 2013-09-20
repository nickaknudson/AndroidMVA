/**
 * 
 */
package com.nickaknudson.mva.clients;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.nickaknudson.mva.Client;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface ModelClient<T extends Model<T>> extends Client {
	abstract Type getType();
	abstract TypeToken<T> getTypeToken();
}
