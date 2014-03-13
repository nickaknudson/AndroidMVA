package com.nickaknudson.mva.callbacks;

import java.util.Collections;
import java.util.LinkedList;



/**
 * @author nick
 *
 * @param <C> callback type
 */
public class CallbackManager<C extends ManagedCallback> implements ManagedCallback {
	
	private LinkedList<C> managedCallbacks = new LinkedList<C>();
	private LinkedList<CallbackManagerTrigger<C>> replayTriggers = new LinkedList<CallbackManagerTrigger<C>>();
	
	/**
	 * Add a callback to the callback queue
	 * @param callback
	 * @return added
	 */
	public synchronized boolean add(C callback) {
		if(callback != null && !managedCallbacks.contains(callback)) {
			if(callback.replay()) {
				for(CallbackManagerTrigger<C> replayCallback : replayTriggers) {
					replayCallback.triggerCallback(callback);
				}
			}
			return managedCallbacks.add(callback);
		} else {
			return false;
		}
	}
	
	/**
	 * Remove the specified callback from the callback queue
	 * @param callback
	 * @return removed
	 */
	public synchronized boolean remove(C callback) {
		return managedCallbacks.remove(callback);
	}
	
	/**
	 * @param trigger
	 */
	protected synchronized void each(CallbackManagerTrigger<C> trigger) {
		/* CALLBACKS */
		if(managedCallbacks != null && managedCallbacks.size() > 0) {
			/* remove null callbacks */
			managedCallbacks.removeAll(Collections.singleton(null));
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
			for(C managedCallback : managedCallbacks) {
				trigger.triggerCallback(managedCallback);
				if(managedCallback.once()) remove(managedCallback);
			}
		}
		/* REPLAY CALLBACKS */
		replayTriggers.add(trigger);
	}
	
	/**
	 * @return callback count
	 */
	public int count() {
		return managedCallbacks.size();
	}
	
	/**
	 * removes all callbacks
	 */
	public synchronized void removeAll() {
		managedCallbacks.clear();
	}

	@Override
	public boolean replay() {
		return true;
	}

	@Override
	public boolean once() {
		return false;
	}
}
