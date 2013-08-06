/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface UpdateCallback<T extends Model<T>> extends ErrorCallback {
	public void onUpdate(T model);
}
