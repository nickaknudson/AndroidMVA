/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface ReadCallback<T extends Model<T>> extends ErrorCallback {
	public void onRead(T model);
}
