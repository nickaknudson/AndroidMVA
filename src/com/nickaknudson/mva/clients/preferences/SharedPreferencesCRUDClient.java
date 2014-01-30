/**
 * 
 */
package com.nickaknudson.mva.clients.preferences;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.SharedPreferences;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DeleteCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;
import com.nickaknudson.mva.clients.CRUDClient;

/**
 * @author nick
 * @param <T> 
 *
 */
public abstract class SharedPreferencesCRUDClient<T extends Model<T>> implements CRUDClient<T> {
	
	private Context context;

	/**
	 * @param context
	 * @param settings
	 */
	public SharedPreferencesCRUDClient(Context context) {
		this.context = context;
	}

	/**
	 * Shared Preferences SETTINGS_NAME
	 * @return SETTINGS_NAME
	 */
	public abstract String getSettingsName();

	/**
	 * @return type
	 */
	public Type getType() {
		return getTypeToken().getType();
	}

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.CRUDClient#create(com.nickaknudson.mva.Model, com.nickaknudson.mva.callbacks.CreateCallback)
	 */
	@Override
	public void create(final T model, final CreateCallback<T> callback) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(getSettingsName(), Context.MODE_PRIVATE);
		create(model, sharedPreferences, callback);
	}

	/**
	 * Get an editor with sharedPreferences.edit() and remember to call editor.commit()
	 * 
	 * @param model
	 * @param sharedPreferences
	 * @param callback
	 */
	protected abstract void create(final T model, SharedPreferences sharedPreferences, final CreateCallback<T> callback);

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.CRUDClient#read(com.nickaknudson.mva.Model, com.nickaknudson.mva.callbacks.ReadCallback)
	 */
	@Override
	public void read(final T model, final ReadCallback<T> callback) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(getSettingsName(), Context.MODE_PRIVATE);
		read(model, sharedPreferences, callback);
	}

	/**
	 * Read from sharedPreferences
	 * 
	 * @param model
	 * @param sharedPreferences
	 * @param callback
	 */
	protected abstract void read(final T model, SharedPreferences sharedPreferences, final ReadCallback<T> callback);

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.CRUDClient#update(com.nickaknudson.mva.Model, com.nickaknudson.mva.callbacks.UpdateCallback)
	 */
	@Override
	public void update(T model, UpdateCallback<T> callback) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(getSettingsName(), Context.MODE_PRIVATE);
		update(model, sharedPreferences, callback);
	}

	/**
	 * Get an editor with sharedPreferences.edit() and remember to call editor.commit()
	 * 
	 * @param model
	 * @param sharedPreferences
	 * @param callback
	 */
	protected abstract void update(final T model, SharedPreferences sharedPreferences, final UpdateCallback<T> callback);

	/* (non-Javadoc)
	 * @see com.nickaknudson.mva.clients.CRUDClient#destroy(com.nickaknudson.mva.Model, com.nickaknudson.mva.callbacks.DeleteCallback)
	 */
	@Override
	public void destroy(final T model, final DeleteCallback callback) {
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(getSettingsName(), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.clear();
			editor.commit();
			if(callback != null) callback.onDelete();
		} catch(Exception e) {
			if(callback != null) callback.onError(e);
		}
	}

}
