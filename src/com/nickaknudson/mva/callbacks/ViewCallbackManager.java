/**
 * 
 */
package com.nickaknudson.mva.callbacks;

import android.view.View;

/**
 * @author nick
 *
 */
public class ViewCallbackManager extends CallbackManager<ViewCallback> implements ViewCallback {

	@Override
	public void onView(final View view) {
		each(new CallbackManagerTrigger<ViewCallback>() {
			@Override
			public void triggerCallback(ViewCallback callback) {
				callback.onView(view);
			}
		});
	}
}
