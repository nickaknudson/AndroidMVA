package com.nickaknudson.mva.clients;

import java.util.Iterator;
import java.util.LinkedList;

import com.nickaknudson.mva.Model;

public class SRClientCallbackManager<T extends Model> implements SRClientCallback<T> {
	
	private LinkedList<SRClientCallback<T>> callbacks = new LinkedList<SRClientCallback<T>>();
	
	public void add(final SRClientCallback<T> callback) {
		if(callback != null && !callbacks.contains(callback))
			callbacks.add(callback);
	}
	
	public boolean remove(SRClientCallback<T> callback) {
		return callbacks.remove(callback);
	}

	@Override
	public void onRecieve(T model) {
		if(callbacks != null && callbacks.size() > 0) {
			Iterator<SRClientCallback<T>> iter = callbacks.iterator();
	        while (iter.hasNext()) {
	        	SRClientCallback<T> cb = iter.next();
	        	if(cb != null) {
	        		cb.onRecieve(model);
	        	} else {
	        		iter.remove();
	        	}
	        }
		}
	}
}
