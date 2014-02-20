package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> model type
 */
public class SetCallbackManager<M extends Model<M>> extends CallbackManager<SetCallback<M>> implements SetCallback<M> {

	@Override
	public void onSet(final M model) {
		each(new CallbackManagerCallback<SetCallback<M>>() {
			@Override
			public void onCallback(SetCallback<M> callback) {
				callback.onSet(model);
			}
		});
	}
}
