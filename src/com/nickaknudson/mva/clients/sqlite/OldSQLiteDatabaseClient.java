/**
 *
 */
package com.nickaknudson.mva.clients.sqlite;

import java.lang.reflect.Type;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.PersistentCallback;
import com.nickaknudson.mva.callbacks.PersistentCallbackManager;
import com.nickaknudson.mva.clients.CRUDClient;
import com.nickaknudson.mva.clients.PersistentClient;
import com.nickaknudson.mva.exceptions.NotConnectedException;

/**
 * @author nick
 * @param <M>
 * @param <H>
 */
public abstract class OldSQLiteDatabaseClient<M extends Model<M>, H extends SQLiteOpenHelper>
		implements CRUDClient<M>, PersistentClient {

	private PersistentCallbackManager pcallbacks = new PersistentCallbackManager();
	private H helper;
	private SQLiteDatabase db;
	private boolean connected = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nickaknudson.mva.clients.PersistentClient#connect(com.nickaknudson
	 * .mva.callbacks.PersistentCallback)
	 */
	@Override
	public void connect(PersistentCallback callback) {
		add(callback);
		Thread thread = new Thread() {
			public void run() {
				try {
					db = helper.getWritableDatabase();
					connected = true;
					pcallbacks.onConnected();
				} catch (Exception e) {
					pcallbacks.onError(e);
				}
			}
		};
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nickaknudson.mva.clients.PersistentClient#addConnectCallback(com.
	 * nickaknudson.mva.callbacks.PersistentCallback)
	 */
	@Override
	public boolean add(PersistentCallback callback) {
		return pcallbacks.add(callback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nickaknudson.mva.clients.PersistentClient#removeConnectCallback(com
	 * .nickaknudson.mva.callbacks.PersistentCallback)
	 */
	@Override
	public boolean remove(PersistentCallback callback) {
		return pcallbacks.remove(callback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nickaknudson.mva.clients.PersistentClient#disconnect()
	 */
	@Override
	public void disconnect() {
		connected = false;
		db.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nickaknudson.mva.clients.PersistentClient#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return connected;
	}

	protected SQLiteDatabase getDB() throws NotConnectedException {
		if (db != null) {
			return db;
		} else {
			throw new NotConnectedException();
		}
	}

	/**
	 * @return helper
	 */
	public H getHelper() {
		return helper;
	}

	/**
	 * @param helper
	 */
	public void setHelper(H helper) {
		this.helper = helper;
	}

	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}
}