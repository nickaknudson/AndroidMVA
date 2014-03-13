package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <M> model type
 */
public class ReceiveCallbackManager<M extends Model<M>> extends CallbackManager<ReceiveCallback<M>> implements ReceiveCallback<M> {
	
	@Override
	public void onReceive(final M model) {
		each(new CallbackManagerTrigger<ReceiveCallback<M>>() {
			@Override
			public void triggerCallback(ReceiveCallback<M> callback) {
				callback.onReceive(model);
			}
		});
	}
}
