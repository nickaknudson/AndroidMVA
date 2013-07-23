package com.nickaknudson.mva.clients;

import java.util.Iterator;
import java.util.LinkedList;

public class PersistentClientCallbackManager implements PersistentClientCallback {
	
	private LinkedList<PersistentClientCallback> callbacks = new LinkedList<PersistentClientCallback>();
	
	public void add(final PersistentClientCallback callback) {
		if(callback != null && !callbacks.contains(callback))
			callbacks.add(callback);
	}
	
	public boolean remove(PersistentClientCallback callback) {
		return callbacks.remove(callback);
	}

	@Override
	public void onConnected(Exception ex) {
		if(callbacks != null && callbacks.size() > 0) {
			Iterator<PersistentClientCallback> iter = callbacks.iterator();
	        while (iter.hasNext()) {
	        	PersistentClientCallback cb = iter.next();
	        	if(cb != null) {
		            cb.onConnected(ex);
	        	} else {
	        		iter.remove();
	        	}
	        }
		}
	}

	@Override
	public void onDisconnected(Exception ex) {
		if(callbacks != null && callbacks.size() > 0) {
			Iterator<PersistentClientCallback> iter = callbacks.iterator();
	        while (iter.hasNext()) {
	        	PersistentClientCallback cb = iter.next();
	        	if(cb != null) {
		            cb.onDisconnected(ex);
	        	} else {
	        		iter.remove();
	        	}
	        }
		}
	}
}
