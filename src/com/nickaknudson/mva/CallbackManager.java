package com.nickaknudson.mva;

import java.util.Vector;

public class CallbackManager<C extends Callback> {
    //interface OnceCallback extends C {}
    

	transient private boolean changed = false;
	transient private Vector callbacks = new Vector();

	public synchronized void addCallback(C o) {
		if (o == null)
			throw new NullPointerException();
			if (!callbacks.contains(o)) {
				callbacks.addElement(o);
			}
	}

	public synchronized void deleteCallback(C o) {
		callbacks.removeElement(o);
	}
       
	public void notifyObservers() {
		notifyObservers(null);
	}

	public void notifyObservers(Object arg) {
           Object[] arrLocal;
   
           synchronized (this) {
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
        	   if (!changed)
        		   return;
        	   arrLocal = callbacks.toArray();
           }
   
           for (int i = arrLocal.length-1; i>=0; i--)
        	   ((C)arrLocal[i]).onCall();
	}

	public synchronized void deleteCallbacks() {
		callbacks.removeAllElements();
	}

	public synchronized int countCallbacks() {
		return callbacks.size();
	}

    //public void once(final String event, final C callback) {
        //on(event, new OnceCallback() {
        //    @Override
        //    public void onCall() {
        //        callback.onCall();
        //    }
        //});
    //}

    public void on(C callback) {
        callbacks.add(callback);
    }
}
