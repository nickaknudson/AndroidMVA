/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import android.view.View;

/**
 * @author nick
 */
public interface ViewCallback extends ManagedCallback {
	/**
	 * @param view
	 */
	public void onView(View view);
}
