package com.nickaknudson.android;

public abstract class Model extends java.util.Observable {
	protected static final String TAG = Model.class.getSimpleName();

	// remember to call setChanged()
	public abstract void set(Model object);
	
	public void set(Model object, boolean notify) {
		set(object);
		if(notify) notifyObservers();
	}
	
	protected void setChangedAndNotify(Object data) {
		setChanged();
		notifyObservers(data);
	}
	
	protected void setChangedAndNotify() {
		setChangedAndNotify(null);
	}
}