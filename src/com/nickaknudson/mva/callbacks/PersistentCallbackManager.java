package com.nickaknudson.mva.callbacks;


public class PersistentCallbackManager extends CallbackManager<PersistentCallback> implements PersistentCallback {

	@Override
	public void onConnected(final Exception ex) {
		each(new CallbackManagerCallback<PersistentCallback>() {

			@Override
			public void onCallback(PersistentCallback callback) {
				callback.onConnected(ex);
			}
		});
	}

	@Override
	public void onDisconnected(final Exception ex) {
		each(new CallbackManagerCallback<PersistentCallback>() {

			@Override
			public void onCallback(PersistentCallback callback) {
				callback.onDisconnected(ex);
			}
		});
	}
}
