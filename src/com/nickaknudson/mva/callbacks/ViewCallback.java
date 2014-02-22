/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import android.view.View;

/**
 * @author nick
 */
public interface ViewCallback extends Callback {
	/**
	 * @param view
	 */
	public void onView(View view);
}
