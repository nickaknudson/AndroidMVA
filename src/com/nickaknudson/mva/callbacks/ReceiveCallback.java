/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface ReceiveCallback<T extends Model<T>> {
	public void onReceive(T model);
}
