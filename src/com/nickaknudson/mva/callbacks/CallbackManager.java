package com.nickaknudson.mva.callbacks;

import java.util.Collections;
import java.util.LinkedList;



/**
 * @author nick
 *
 * @param <C> callback type
 */
public class CallbackManager<C extends Callback> {
	
	private LinkedList<C> callbacks = new LinkedList<C>();
	private LinkedList<C> onceCallbacks = new LinkedList<C>();
	private LinkedList<CallbackManagerCallback<C>> replayCallbacks = new LinkedList<CallbackManagerCallback<C>>();
	
	/**
	 * Add a callback to the callback queue and specify if previous callbacks
	 * should be replayed
	 * @param replay 
	 * @param callback
	 * @return added
	 */
	public synchronized boolean add(boolean replay, C callback) {
		if(callback != null && !callbacks.contains(callback)) {
			if(replay) {
				for(CallbackManagerCallback<C> replayCallback : replayCallbacks) {
					replayCallback.onCallback(callback);
				}
			}
			return callbacks.add(callback);
		} else {
			return false;
		}
	}
	
	/**
	 * Add a callback to the callback queue without replay
	 * @param callback
	 * @return added
	 */
	public boolean add(C callback) {
		// replay is false for compatibility
		return add(false, callback);
	}
	
	/**
	 * Add a callback to the once callback queue and specify if previous
	 * callbacks should be replayed
	 * @param replay
	 * @param onceCallback
	 * @return added
	 */
	public synchronized boolean once(boolean replay, C onceCallback) {
		if(onceCallback != null && !onceCallbacks.contains(onceCallback)) {
			if(replay) {
				if(replayCallbacks.size() > 0) {
					CallbackManagerCallback<C> replayCallback = replayCallbacks.removeLast();
					replayCallback.onCallback(onceCallback);
					return true;
				}
			}
			return onceCallbacks.add(onceCallback);
		} else {
			return false;
		}
	}
	
	/**
	 * Add a callback to the once callback queue without replay
	 * @param onceCallback
	 * @return added
	 */
	public boolean once(C onceCallback) {
		// replay is false for compatibility
		return once(false, onceCallback);
	}
	
	/**
	 * Remove the specified callback from the callback queue
	 * @param callback
	 * @return removed
	 */
	public synchronized boolean remove(C callback) {
		return callbacks.remove(callback) || onceCallbacks.remove(callback);
	}
	
	/**
	 * @param callback
	 */
	protected void each(CallbackManagerCallback<C> callback) {
		/* CALLBACKS */
		if(callbacks != null && callbacks.size() > 0) {
			synchronized(this) {
				// remove null callbacks
				callbacks.removeAll(Collections.singleton(null));
				/* We don't want the observer doing callbacks into arbitrary
				 * code while holding its own Monitor. The code where we extract
				 * each observable from the List and store the state of the
				 * observer needs synchronization, but notifying observers does
				 * not (should not). The worst result of any potential
				 * race-condition here is that:
				 * 1) newly-added observer will miss a notification in progress
				 * 2) recently unregistered Observer will be wrongly notified
				 *      when it doesn't care
				 */
				for(C cb : callbacks) {
					callback.onCallback(cb);
				}
			}
		}
		/* ONCE CALLBACKS */
		if(onceCallbacks != null && onceCallbacks.size() > 0) {
			synchronized(this) {
				// remove null callbacks
				onceCallbacks.removeAll(Collections.singleton(null));
				for(C cb : onceCallbacks) {
					callback.onCallback(cb);
					remove(cb);
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
	public synchronized void removeAll() {
		callbacks.clear();
		onceCallbacks.clear();
	}
}
