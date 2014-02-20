package com.nickaknudson.mva.callbacks;

/**
 * @author nick
 *
 */
public class PersistentCallbackManager extends CallbackManager<PersistentCallback> implements PersistentCallback {

	@Override
	public void onConnected() {
		each(new CallbackManagerCallback<PersistentCallback>() {
			@Override
			public void onCallback(PersistentCallback callback) {
				callback.onConnected();
			}
		});
	}

	@Override
	public void onDisconnected() {
		each(new CallbackManagerCallback<PersistentCallback>() {
			@Override
			public void onCallback(PersistentCallback callback) {
				callback.onDisconnected();
			}
		});
	}

	@Override
	public void onError(final Exception e) {
		each(new CallbackManagerCallback<PersistentCallback>() {
			@Override
			public void onCallback(PersistentCallback callback) {
				callback.onError(e);
			}
		});
	}
}
