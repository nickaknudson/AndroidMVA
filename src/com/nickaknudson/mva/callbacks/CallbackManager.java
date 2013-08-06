package com.nickaknudson.mva.callbacks;

import java.util.Iterator;
import java.util.LinkedList;


public class CallbackManager<C> {
	
	private LinkedList<C> callbacks = new LinkedList<C>();
	
	public boolean add(C callback) {
		if(callback != null && !callbacks.contains(callback)) {
			return callbacks.add(callback);
		} else {
			return false;
		}
	}
	
	public boolean remove(C callback) {
		return callbacks.remove(callback);
	}
	
	protected void each(CallbackManagerCallback<C> callback) {
		if(callbacks != null && callbacks.size() > 0) {
			Iterator<C> iter = callbacks.iterator();
	        while (iter.hasNext()) {
	        	C cb = iter.next();
	        	if(cb == null) {
	        		iter.remove();
	        	} else {
		            callback.onCallback(cb);
	        	}
	        }
		}
	}
}
