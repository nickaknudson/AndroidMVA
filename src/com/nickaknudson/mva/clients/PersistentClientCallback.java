package com.nickaknudson.mva.clients;

public interface PersistentClientCallback {
	public void onConnected(Exception ex);
	public void onDisconnected(Exception ex);
}
