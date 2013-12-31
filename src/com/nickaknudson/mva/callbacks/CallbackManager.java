package com.nickaknudson.mva.callbacks;

import java.util.Iterator;
import java.util.LinkedList;



/**
 * @author nick
 *
 * @param <C> callback type
 */
public class CallbackManager<C extends Callback> {
	
	private LinkedList<C> callbacks = new LinkedList<C>();
	
	/**
	 * @param callback
	 * @return added
	 */
	public boolean add(C callback) {
		if(callback != null && !callbacks.contains(callback)) {
			return callbacks.add(callback);
		} else {
			return false;
		}
	}
	
	/**
	 * @param callback
	 * @return removed
	 */
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
	
	/**
	 * @return callback count
	 */
	public int count() {
		return callbacks.size();
	}
	
	/**
	 * removes all callbacks
	 */
	public void removeAll() {
		callbacks.clear();
	}
}
