package com.nickaknudson.mva.callbacks;


public interface CallbackManagerCallback<C> extends Callback {
	public void onCallback(C callback);
}
