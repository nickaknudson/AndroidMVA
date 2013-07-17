package com.nickaknudson.mva;

public abstract class Model extends Observable {
	protected static final String TAG = Model.class.getSimpleName();
	
	transient private boolean _new = true;

	public boolean isNew() {
		return _new;
	}

	protected void setNew(boolean n) {
		_new = n;
	}

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