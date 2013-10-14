package com.nickaknudson.mva;

public abstract class Model<M extends Model<M>> extends Observable {
	protected static final String TAG = Model.class.getSimpleName();
	
	transient private boolean _new = true;

	public boolean isNew() {
		return _new;
	}

	protected void setNew(boolean n) {
		_new = n;
	}

	/**
	 * Remember to call set() if the model has changed
	 * @param model
	 */
	public abstract void set(M model);
	
	protected void setChangedAndNotify(Object data) {
		setChanged();
		notifyObservers(data);
	}
	
	protected void setChangedAndNotify() {
		setChangedAndNotify(null);
	}
	
	protected void set() { // TODO
		setChangedAndNotify();
	}
}