package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> model type
 */
public class SetCallbackManager<T extends Model<T>> extends CallbackManager<SetCallback<T>> implements SetCallback<T> {

	@Override
	public void onSet(final T model) {
		each(new CallbackManagerCallback<SetCallback<T>>() {

			@Override
			public void onCallback(SetCallback<T> callback) {
				callback.onSet(model);
			}
			
		});
	}
}
