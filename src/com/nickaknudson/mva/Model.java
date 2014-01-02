package com.nickaknudson.mva;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class Model<M extends Model<M>> extends Observable {
	protected static final String TAG = Model.class.getSimpleName();
	
	transient private boolean _new = true;

	/**
	 * @return isNew
	 */
	public boolean isNew() {
		return _new;
	}

	protected void setNew(boolean n) {
		_new = n;
	}

	/**
	 * Clients can call this to do a mass attribute update.
	 * Your implementation determines under what circumstances attributes get updated.
	 * Remember to call {@link #setChanged()} if the model has changed.
	 * Remember to call {@link #setChangedAndNotify()} if you would like to notify observers of the model's change.
	 * @param model
	 */
	public abstract void set(M model);

	/**
	 * Convenience method to invoke {@link #setChanged()} and notify observers with data
	 */	
	protected void setChangedAndNotify(Object data) {
		setChanged();
		notifyObservers(data);
	}
	
	/**
	 * Convenience method to invoke {@link #setChanged()} and notify observers
	 */
	protected void setChangedAndNotify() {
		setChangedAndNotify(null);
	}
	
	protected void set() { // TODO
		setChangedAndNotify();
	}
}