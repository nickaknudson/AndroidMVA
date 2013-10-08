/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface PushCallback<T extends Model<T>> extends ErrorCallback {
	public void onPush(T model);
}
