/**
 * 
 */
package com.nickaknudson.mva.clients.preferences;

import com.google.gson.Gson;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author nick
 * @param <T> 
 *
 */
public abstract class SimpleSharedPreferencesCRUDClient<T extends Model<T>> extends SharedPreferencesCRUDClient<T> {

	/** JSON column */
	private static final String JSON = "JSON";
	/** GSON */
	private Gson gson = new Gson();

	/**
	 * @param context
	 */
	public SimpleSharedPreferencesCRUDClient(Context context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.preferences.SharedPreferencesCRUDClient#create(com.nickaknudson.mva.Model, android.content.SharedPreferences, com.nickaknudson.mva.callbacks.CreateCallback)
	 */
	@Override
	protected void create(T model, SharedPreferences sharedPreferences, CreateCallback<T> callback) {
		try {
			Editor editor = sharedPreferences.edit();
			String json = gson.toJson(model, getType());
			editor.putString(JSON, json);
			editor.commit();
			if(callback != null) callback.onCreate(model);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.preferences.SharedPreferencesCRUDClient#read(com.nickaknudson.mva.Model, android.content.SharedPreferences, com.nickaknudson.mva.callbacks.ReadCallback)
	 */
	@Override
	protected void read(T model, SharedPreferences sharedPreferences, ReadCallback<T> callback) {
		try {
			String json = sharedPreferences.getString(JSON, null);
			if(json == null) {
				json = "{}";
			}
			T rmodel = gson.fromJson(json, getType());
			model.set(rmodel);
			if(callback != null) callback.onRead(model);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.preferences.SharedPreferencesCRUDClient#update(com.nickaknudson.mva.Model, android.content.SharedPreferences, com.nickaknudson.mva.callbacks.UpdateCallback)
	 */
	@Override
	protected void update(T model, SharedPreferences sharedPreferences, UpdateCallback<T> callback) {
		try {
			Editor editor = sharedPreferences.edit();
			String json = gson.toJson(model, getType());
			editor.putString(JSON, json);
			editor.commit();
			if(callback != null) callback.onUpdate(model);
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

}
