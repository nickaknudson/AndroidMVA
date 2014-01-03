package com.nickaknudson.mva.callbacks;

import java.util.Collections;
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
	public synchronized boolean add(C callback) {
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
	public synchronized boolean remove(C callback) {
		return callbacks.remove(callback);
	}
	
	@SuppressWarnings("unchecked")
	protected void each(CallbackManagerCallback<C> callback) {
		if(callbacks != null && callbacks.size() > 0) {
			Iterator<C> iter;
			synchronized(this ) {
				callbacks.removeAll(Collections.singleton(null));
				iter = ((LinkedList<C>) callbacks.clone()).iterator();
			}
			while (iter != null && iter.hasNext()) {
				C cb = iter.next();
				callback.onCallback(cb);
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
	public synchronized void removeAll() {
		callbacks.clear();
	}
}
