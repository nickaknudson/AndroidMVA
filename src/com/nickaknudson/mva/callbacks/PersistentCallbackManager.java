package com.nickaknudson.mva.callbacks;

/**
 * @author nick
 *
 */
public class PersistentCallbackManager extends CallbackManager<PersistentCallback> implements PersistentCallback {

	@Override
	public void onConnected() {
		each(new CallbackManagerTrigger<PersistentCallback>() {
			@Override
			public void triggerCallback(PersistentCallback callback) {
				callback.onConnected();
			}
		});
	}

	@Override
	public void onDisconnected() {
		each(new CallbackManagerTrigger<PersistentCallback>() {
			@Override
			public void triggerCallback(PersistentCallback callback) {
				callback.onDisconnected();
			}
		});
	}

	@Override
	public void onError(final Exception e) {
		each(new CallbackManagerTrigger<PersistentCallback>() {
			@Override
			public void triggerCallback(PersistentCallback callback) {
				callback.onError(e);
			}
		});
	}
}
