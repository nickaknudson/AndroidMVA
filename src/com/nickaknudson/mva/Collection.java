package com.nickaknudson.mva;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class Collection<T> extends Observable implements java.util.List<T> {
	protected static final String TAG = Collection.class.getSimpleName();
	
	public LinkedList<T> collection;
	
	public Collection() {
		collection = new LinkedList<T>();
	}
	
	protected void setChangedAndNotify(Object data) {
		setChanged();
		notifyObservers(data);
	}
	
	protected void setChangedAndNotify() {
		setChangedAndNotify(null);
	}
	
	/*
	 * List Implementation
	 * @see java.util.List#add(java.lang.Object)
	 */

	@Override
	public boolean add(T object) {
		boolean r = collection.add(object);
		setChangedAndNotify(object);
		return r;
	}

	@Override
	public void add(int location, T object) {
		collection.add(location, object);
		setChangedAndNotify(object);
	}

	@Override
	public boolean addAll(java.util.Collection<? extends T> c) {
		boolean r = collection.addAll(c);
		setChangedAndNotify(c);
		return r;
	}

	@Override
	public boolean addAll(int location, java.util.Collection<? extends T> c) {
		boolean r = collection.addAll(location, c);
		setChangedAndNotify(c);
		return r;
	}

	@Override
	public void clear() {
		collection.clear();
		setChangedAndNotify();
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
	public T get(int location) {
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
	public Iterator<T> iterator() {
		return collection.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return collection.lastIndexOf(object);
	}

	@Override
	public ListIterator<T> listIterator() {
		return collection.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int location) {
		return collection.listIterator(location);
	}

	@Override
	public T remove(int location) {
		T r = collection.remove(location);
		setChangedAndNotify(r);
		return r;
	}

	@Override
	public boolean remove(Object object) {
		boolean r = collection.remove(object);
		setChangedAndNotify(object);
		return r;
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c) {
		boolean r = collection.removeAll(c);
		setChangedAndNotify(c);
		return r;
	}

	@Override
	public boolean retainAll(java.util.Collection<?> c) {
		boolean r = collection.retainAll(c);
		setChangedAndNotify(c);
		return r;
	}

	@Override
	public T set(int location, T object) {
		T r = collection.set(location, object);
		setChangedAndNotify(object);
		return r;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public List<T> subList(int start, int end) {
		return collection.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return collection.toArray(array);
	}
}
