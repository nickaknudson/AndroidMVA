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
				/* We don't want the Observer doing callbacks into
				* arbitrary code while holding its own Monitor.
				* The code where we extract each Observable from
				* the Vector and store the state of the Observer
				* needs synchronization, but notifying observers
				* does not (should not). The worst result of any
				* potential race-condition here is that:
				* 1) a newly-added Observer will miss a
				*   notification in progress
				* 2) a recently unregistered Observer will be
				*   wrongly notified when it doesn't care
				*/
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
