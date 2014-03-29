package com.nickaknudson.mva.clients.sqlite;

import java.security.InvalidParameterException;
import java.util.LinkedList;

import com.nickaknudson.mva.callbacks.ErrorCallback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @author nick
 * 
 */
public abstract class SQLiteDatabaseClientHelper {
	private static final String TAG = SQLiteDatabaseClientHelper.class.getSimpleName();

	/**
	 * The database open helper
	 */
	public SQLiteOpenHelper helper;
	
	/**
	 * @param context
	 */
	public SQLiteDatabaseClientHelper(Context context) {
		helper = new SQLiteOpenHelper(context, getDBName(), null, getDBVersion(), null) {

			@Override
			public void onCreate(SQLiteDatabase db) {
				String sql;
				sql = "CREATE TABLE " + getTableName() + " (";
				sql = sql + BaseColumns._ID + " INTEGER PRIMARY KEY";
				for(int i = 0; i < getColumnCount(); i++) {
					ColumnInformation info = getColumnInformation(i);
					switch(info.columnType()) {
					case STRING:
						sql = sql + ", " + info.columnName() + " TEXT";
						break;
					case INTEGER:
					case LONG:
					case BOOLEAN:
						sql = sql + ", " + info.columnName() + " INTEGER";
						break;
					}
				}
				sql = sql + ");";
				db.execSQL(sql);
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// Logs that the database is being upgraded
				Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
				// Kills the table and existing data
				db.execSQL("DROP TABLE IF EXISTS " + getTableName());
				// Recreates the database with a new version
				onCreate(db);
			}
		};
	}
	
	/**
	 * @return database
	 */
	public SQLiteDatabase getWritableDatabase() {
		return helper.getWritableDatabase();
	}
	
	/**
	 * @return database
	 */
	public SQLiteDatabase getReadableDatabase() {
		return helper.getReadableDatabase();
	}
	
	/**
	 * @author nick
	 */
	public interface DBInsertCallback extends ErrorCallback {
		/**
		 * @param row the row ID of the newly inserted row
		 */
		public void onInsert(long row);
		/**
		 * @param cIndex
		 * @return value
		 */
		public Object value(int cIndex);
	}

	/**
	 * @param db
	 * @param insert
	 * @throws SQLException
	 */
	public void insert(SQLiteDatabase db, DBInsertCallback insert) throws SQLException {
		if(insert == null) {
			throw new InvalidParameterException("InsertCallback cannot be null");
		}
		ContentValues values = new ContentValues(getColumnCount());
		for(int i = 0; i < getColumnCount(); i++) {
			ColumnInformation info = getColumnInformation(i);
			String columnName = info.columnName();
			switch(info.columnType()) {
			case STRING:
				values.put(columnName, (String) insert.value(i));
				break;
			case INTEGER:
				values.put(columnName, (Integer) insert.value(i));
				break;
			case LONG:
				values.put(columnName, (Long) insert.value(i));
				break;
			case BOOLEAN:
				values.put(columnName, (Boolean) insert.value(i));
				break;
			}
		}
		// the row ID of the newly inserted row, or -1 if an error occurred
		long row = db.insertOrThrow(getTableName(), null, values);
		if(row > -1) {
			insert.onInsert(row);
		}
	}
	
	/**
	 * @author nick
	 */
	public interface DBQueryCallback {
		/**
		 * @param cursor
		 */
		public void onQuery(Cursor cursor);
		/**
		 * @param count
		 */
		public void onCount(int count);
		/**
		 * @param mIndex
		 * @param cIndex
		 * @param value
		 */
		public void value(int mIndex, int cIndex, Object value);
	}
	
	/**
	 * @param db
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @param query
	 * @return count
	 */
	public int query(SQLiteDatabase db, Integer[] columns, String selection, 
			String[] selectionArgs, String groupBy, String having, 
			String orderBy, String limit, DBQueryCallback query) {
		if(query == null) {
			throw new InvalidParameterException("QueryCallback cannot be null");
		}
		/* column name array */
		String[] columnNameArray = null;
		if(columns != null && columns.length > 0) {
			/* column name list */
			LinkedList<String> columnNameList = new LinkedList<String>();
			for(int i : columns) {
				ColumnInformation info = getColumnInformation(i);
				columnNameList.add(info.columnName());
			}
			/* column name array */
			if(columnNameList.size() > 0) {
				columnNameArray = (String[]) columnNameList.toArray();
			}
		}
		/* query */
		Cursor c = db.query(getTableName(), columnNameArray, selection, selectionArgs, groupBy, having, orderBy, limit);
		/* results */
		int count = c.getCount();
		query.onCount(count);
		for(int j = 0; j < count; j++) {
			// if we have somehow moved past the end of the cursor
			if(!c.moveToNext()) continue;
			for(int i = 0; i < getColumnCount(); i++) {
				ColumnInformation info = getColumnInformation(i);
				Integer columnIndex = c.getColumnIndex(info.columnName());
				if(columnIndex > -1) {
					switch(info.columnType()) {
					case STRING:
						query.value(j, i, c.getString(columnIndex));
						break;
					case INTEGER:
						query.value(j, i, c.getInt(columnIndex));
						break;
					case LONG:
						query.value(j, i, c.getLong(columnIndex));
						break;
					case BOOLEAN:
						query.value(j, i, (c.getInt(columnIndex) == 1) ? true : false);
						break;
					}
				}
			}
		}
		/* query completed */
		query.onQuery(c);
		/* close cursor */
		c.close();
		return count;
	}

	/**
	 * @param db
	 * @param selection
	 * @param selectionArgs
	 * @param query
	 * @return count
	 */
	public int query(SQLiteDatabase db, DBQueryCallback query) {
		return query(db, null, null, null, null, null, null, null, query);
	}

	/**
	 * @param db
	 * @param selection
	 * @param selectionArgs
	 * @param query
	 * @return count
	 */
	public int query(SQLiteDatabase db, String selection, 
			String[] selectionArgs, DBQueryCallback query) {
		return query(db, null, selection, selectionArgs, null, null, null, null, query);
	}
	
	/**
	 * @param db
	 * @param selection
	 * @param selectionArgs
	 * @param limit
	 * @param query
	 * @return count
	 */
	public int query(SQLiteDatabase db, String selection, 
			String[] selectionArgs, String limit, DBQueryCallback query) {
		return query(db, null, selection, selectionArgs, null, null, null, limit, query);
	}
	
	/**
	 * @author nick
	 */
	public interface DBUpdateCallback {
		/**
		 * @param rows the number of rows affected
		 */
		public void onUpdate(int rows);
		/**
		 * @param cIndex
		 * @return value
		 */
		public Object value(int cIndex);
	}
	
	/**
	 * @param db
	 * @param whereClause
	 * @param whereArgs
	 * @param update
	 */
	public void update(SQLiteDatabase db, String whereClause, 
			String[] whereArgs, DBUpdateCallback update) {
		ContentValues values = new ContentValues(getColumnCount());
		for(int i = 0; i < getColumnCount(); i++) {
			ColumnInformation info = getColumnInformation(i);
			String columnName = info.columnName();
			switch(info.columnType()) {
			case STRING:
				values.put(columnName, (String) update.value(i));
				break;
			case INTEGER:
				values.put(columnName, (Integer) update.value(i));
				break;
			case LONG:
				values.put(columnName, (Long) update.value(i));
				break;
			case BOOLEAN:
				values.put(columnName, (Boolean) update.value(i));
				break;
			}
		}
		int rows = db.update(getTableName(), values, whereClause, whereArgs);
		update.onUpdate(rows);
	}
	
	/**
	 * @author nick
	 */
	public interface DBDeleteCallback {
		/**
		 * @param rows the number of rows affected if a whereClause is passed
		 * in,0 otherwise. To remove all rows and get a count pass "1" as the
		 * whereClause.
		 */
		public void onDelete(int rows);
	}
	
	/**
	 * @param db
	 * @param whereClause
	 * @param whereArgs
	 * @param delete
	 */
	public void delete(SQLiteDatabase db, String whereClause, 
			String[] whereArgs, DBDeleteCallback delete) {
		int rows = db.delete(getTableName(), whereClause, whereArgs);
		delete.onDelete(rows);
	}
	
	/**
	 * @param context
	 * @return true if the database was successfully deleted; else false.
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(getDBName());
	}

	/**
	 * Something in the form of name.db
	 * @return database file name
	 */
	protected abstract String getDBName();
	protected abstract Integer getDBVersion();
	protected abstract String getTableName();

	protected abstract Integer getColumnCount();
	
	/**
	 * @author nick
	 */
	public enum ColumnType {
		/** STRING */
		STRING,
		/** INTEGER */
		INTEGER,
		/** LONG */
		LONG,
		/** BOOLEAN */
		BOOLEAN
	}
	
	protected interface ColumnInformation {
		public String columnName();
		public ColumnType columnType();
	}
	
	protected abstract ColumnInformation getColumnInformation(Integer index);
}
