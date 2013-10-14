/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 */
public interface IndexCallback<T extends Model<T>> extends ErrorCallback {
	public void onFetch(Collection<T> collection);
}
