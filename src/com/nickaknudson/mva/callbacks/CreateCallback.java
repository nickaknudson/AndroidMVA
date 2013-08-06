/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface CreateCallback<T extends Model<T>> extends ErrorCallback {
	public void onCreate(T model);
}
