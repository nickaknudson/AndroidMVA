package com.nickaknudson.mva;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author nick
 *
 * @param <M> model type of collection
 */
public class Collection<M extends Model<M>> implements java.util.List<M> {
	protected static final String TAG = Collection.class.getSimpleName();
	
	protected LinkedList<M> collection = new LinkedList<M>();
	transient private LinkedList<CollectionObserver<M>> observers = new LinkedList<CollectionObserver<M>>();

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
				for(CollectionObserver<M> observer : observers) {
					observer.onChange(this, data);
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
	public synchronized boolean add(CollectionObserver<M> observer) {
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
	public synchronized boolean remove(CollectionObserver<M> observer) {
		return observers.remove(observer);
	}
	
	/**
	 * Removes all observers
	 */
	public synchronized void removeAll() {
		observers.clear();
	}
	
	/*
	 * List Implementation
	 * @see java.util.List#add(java.lang.Object)
	 */

	@Override
	public boolean add(M object) {
		boolean r = collection.add(object);
		changed(object);
		return r;
	}

	@Override
	public void add(int location, M object) {
		collection.add(location, object);
		changed(object);
	}

	@Override
	public boolean addAll(java.util.Collection<? extends M> c) {
		boolean r = collection.addAll(c);
		changed(c);
		return r;
	}

	@Override
	public boolean addAll(int location, java.util.Collection<? extends M> c) {
		boolean r = collection.addAll(location, c);
		changed(c);
		return r;
	}

	@Override
	public void clear() {
		collection.clear();
		changed();
	}

	@Override
	public boolean contains(Object object) {
		return collection.contains(object);
	}

	@Override
	public boolean containsAll(java.util.Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public M get(int location) {
		return collection.get(location);
	}

	@Override
	public int indexOf(Object object) {
		return collection.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Override
	public Iterator<M> iterator() {
		return collection.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return collection.lastIndexOf(object);
	}

	@Override
	public ListIterator<M> listIterator() {
		return collection.listIterator();
	}

	@Override
	public ListIterator<M> listIterator(int location) {
		return collection.listIterator(location);
	}

	@Override
	public M remove(int location) {
		M r = collection.remove(location);
		changed(r);
		return r;
	}

	@Override
	public boolean remove(Object object) {
		boolean r = collection.remove(object);
		changed(object);
		return r;
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c) {
		boolean r = collection.removeAll(c);
		changed(c);
		return r;
	}

	@Override
	public boolean retainAll(java.util.Collection<?> c) {
		boolean r = collection.retainAll(c);
		changed(c);
		return r;
	}

	@Override
	public M set(int location, M object) {
		M r = collection.set(location, object);
		changed(object);
		return r;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public List<M> subList(int start, int end) {
		return collection.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@Override
	@SuppressWarnings("hiding")
	public <M> M[] toArray(M[] array) {
		return collection.toArray(array);
	}
}
