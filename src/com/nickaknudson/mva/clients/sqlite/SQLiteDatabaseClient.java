/**
 * 
 */
package com.nickaknudson.mva.clients.sqlite;

import java.lang.reflect.Type;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nickaknudson.mva.Collection;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DestroyCallback;
import com.nickaknudson.mva.callbacks.IndexCallback;
import com.nickaknudson.mva.callbacks.PersistentCallback;
import com.nickaknudson.mva.callbacks.PersistentCallbackManager;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;
import com.nickaknudson.mva.clients.CRUDClient;
import com.nickaknudson.mva.clients.IndexClient;
import com.nickaknudson.mva.clients.PersistentClient;
import com.nickaknudson.mva.exceptions.NotConnectedException;

/**
 * @author nick
 * @param <M> 
 * @param <H> 
 */
public abstract class SQLiteDatabaseClient<M extends Model<M>> implements IndexClient<M>, CRUDClient<M>, PersistentClient {

	private PersistentCallbackManager pcallbacks = new PersistentCallbackManager();
	private SQLiteDatabaseClientHelper helper;
	private SQLiteDatabase db;
	private boolean connected = false;
	
	@Override
	public void connect(PersistentCallback callback) {
		add(callback);
		Thread thread = new Thread() {
			public void run() {
				try {
					db = helper.getReadableDatabase();
					connected = true;
					pcallbacks.onConnected();
				} catch (Exception e) {
					pcallbacks.onError(e);
				}
			}
		};
		thread.start();
	}

	@Override
	public boolean add(PersistentCallback callback) {
		return pcallbacks.add(callback);
	}

	@Override
	public boolean remove(PersistentCallback callback) {
		return pcallbacks.remove(callback);
	}

	@Override
	public void disconnect() {
		connected = false;
		db.close();
	}

	@Override
	public boolean isConnected() {
		return connected;
	}
	
	protected SQLiteDatabase getDB() throws NotConnectedException {
		if(db != null) {
			return db;
		} else {
			throw new NotConnectedException();
		}
	}
	
	/**
	 * @throws NotConnectedException
	 */
	public void beginTransaction() throws NotConnectedException {
		getDB().beginTransaction();
	}
	
	/**
	 * @throws NotConnectedException
	 */
	public void setTransactionSuccessful() throws NotConnectedException {
		getDB().setTransactionSuccessful();
	}
	
	/**
	 * @throws NotConnectedException
	 */
	public void endTransaction() throws NotConnectedException {
		getDB().endTransaction();
	}

	/**
	 * @return helper
	 */
	public SQLiteDatabaseClientHelper getHelper() {
		return helper;
	}

	/**
	 * @param helper
	 */
	public void setHelper(SQLiteDatabaseClientHelper helper) {
		this.helper = helper;
	}

	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}

	@Override
	public void index(Collection<M> collection, IndexCallback<M> callback) {
		try {
			SQLiteDatabase db = getDB();
			index(db, collection, callback);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	protected abstract void index(SQLiteDatabase db, Collection<M> collection, IndexCallback<M> callback);

	@Override
	public void create(M model, CreateCallback<M> callback) {
		try {
			SQLiteDatabase db = getDB();
			create(db, model, callback);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	protected abstract void create(SQLiteDatabase db, M model, CreateCallback<M> callback);

	@Override
	public void read(M model, ReadCallback<M> callback) {
		try {
			SQLiteDatabase db = getDB();
			read(db, model, callback);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	protected abstract void read(SQLiteDatabase db, M model, ReadCallback<M> callback);

	@Override
	public void update(M model, UpdateCallback<M> callback) {
		try {
			SQLiteDatabase db = getDB();
			update(db, model, callback);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	protected abstract void update(SQLiteDatabase db, M model, UpdateCallback<M> callback);

	@Override
	public void destroy(M model, DestroyCallback<M> callback) {
		try {
			SQLiteDatabase db = getDB();
			destroy(db, model, callback);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	protected abstract void destroy(SQLiteDatabase db, M model, DestroyCallback<M> callback);
	
	/**
	 * @param context
	 * @return true if the database was successfully deleted; else false.
	 */
	public boolean deleteDatabase(Context context) {
		return helper.deleteDatabase(context);
	}
}
