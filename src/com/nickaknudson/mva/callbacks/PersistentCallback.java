package com.nickaknudson.mva.callbacks;

public interface PersistentCallback extends ErrorCallback {
	public void onConnected();
	public void onDisconnected();
}
