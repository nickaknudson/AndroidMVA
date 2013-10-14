/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface SendCallback<T extends Model<T>> extends ErrorCallback {
	public void onSend(T model);
}
