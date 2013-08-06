/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface FetchCallback<T extends Model<T>> extends ErrorCallback {
	public void onFetch(T model);
}
