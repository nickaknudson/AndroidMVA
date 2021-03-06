package com.nickaknudson.mva;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author nick
 *
 * @param <M> model type
 */
public abstract class Model<M extends Model<M>> {
	protected static final String TAG = Model.class.getSimpleName();
	
	transient private boolean _new = true;
	transient private LinkedList<ModelObserver<M>> observers = new LinkedList<ModelObserver<M>>();

	//private HashMap<String, Property<?>> _properties;

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
	 * Clients can call this to do a mass attribute update. Your implementation
	 * determines under what circumstances attributes get updated. Remember to
	 * call {@link #changed()} if the model has changed. Observers will be
	 * immediately notified of the change.
	 * @param model
	 */
	public abstract void set(M model);
	/* *
	public void set(M model) {
		HashMap<String, Property<?>> properties = getProperties();
		Iterator<Property<?>> iter = properties.values().iterator();
		while(iter.hasNext()) {
			try {
				Property<?> property = iter.next();
				String name = property.getName();
				// get property from model
				// set to property
				model.get(name, new MergeProperty(property) {
					@Override
					public void onSet() {}
				});
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	*/

	/**
	 * Alerts observers that the model has changed. Provides observers a
	 * reference to this model's instance.
	 */
	protected void changed(Object data) {
		if(observers != null && observers.size() > 0) {
			synchronized(this) {
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
				observers.removeAll(Collections.singleton(null));
				for(ModelObserver<M> observer : observers) {
					observer.onChange((M)this, data);
				}
			}
		}
	}
	
	protected void changed() {
		changed(null);
	}
	
	/**
	 * @param observer
	 * @return added
	 */
	public synchronized boolean add(ModelObserver<M> observer) {
		if(observer != null) {
			return observers.add(observer);
		} else {
			return false;
		}
	}
	
	/**
	 * @param observer
	 * @return removed
	 */
	public synchronized boolean remove(ModelObserver<M> observer) {
		return observers.remove(observer);
	}
	
	/**
	 * Removes all observers
	 */
	public synchronized void removeAll() {
		observers.clear();
	}
	
	/**
	 * @return
	 *
	public HashMap<String, Property<?>> getProperties() {
		if(_properties == null) {
			Property<?>[] props = properties();
			_properties = new HashMap<String, Property<?>>();
			if(props != null) {
				for(Property<?> prop : props) {
					_properties.put(prop.getName(), prop);
				}
			}
		}
		return _properties;
	}
	
	/**
	 * @param properties 
	 * @return
	 *
	public abstract Property<?>[] properties();
	
	/**
	 * @param prop
	 * @param getter 
	 * @throws NoSuchFieldException 
	 *
	public void get(String prop, GetProperty getter) throws NoSuchFieldException {
		if(getter == null) throw new InvalidParameterException("getter required");
		Property<?> property = getProperties().get(prop);
		if(property != null) {
			property.get(getter);
		} else {
			throw new NoSuchFieldException(prop);
		}
	}
	
	/**
	 * @param prop
	 * @param setter 
	 * @throws NoSuchFieldException setter
	 *
	public void set(String prop, SetProperty setter) throws NoSuchFieldException {
		if(setter == null) throw new InvalidParameterException("getter required");
		Property<?> property = getProperties().get(prop);
		if(property != null) {
			property.set(setter);
		} else {
			throw new NoSuchFieldException(prop);
		}
	}
	/* */
}