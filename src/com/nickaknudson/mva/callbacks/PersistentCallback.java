package com.nickaknudson.mva.callbacks;

public interface PersistentCallback {
	public void onConnected(Exception ex);
	public void onDisconnected(Exception ex);
}
