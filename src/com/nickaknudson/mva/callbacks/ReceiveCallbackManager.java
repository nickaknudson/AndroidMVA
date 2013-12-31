package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

/**
 * @author nick
 * @param <T> model type
 */
public class ReceiveCallbackManager<T extends Model<T>> extends CallbackManager<ReceiveCallback<T>> implements ReceiveCallback<T> {

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.callback.RecieveCallback#onReceive(com.nickaknudson.mva.Model)
	 */
	@Override
	public void onReceive(final T model) {
		each(new CallbackManagerCallback<ReceiveCallback<T>>() {

			@Override
			public void onCallback(ReceiveCallback<T> callback) {
				callback.onReceive(model);
			}
		});
	}
}
