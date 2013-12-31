package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 *
 * @param <T>
 */
public class ModelCallbackManager<T extends Model<T>> extends CallbackManager<ModelCallback<T>> implements ModelCallback<T> {

	@Override
	public void onModel(final T model) {
		each(new CallbackManagerCallback<ModelCallback<T>>() {

			@Override
			public void onCallback(ModelCallback<T> callback) {
				callback.onModel(model);
			}
		});
	}
}
