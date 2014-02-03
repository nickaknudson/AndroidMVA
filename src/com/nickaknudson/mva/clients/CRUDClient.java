package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.CreateCallback;
import com.nickaknudson.mva.callbacks.DestroyCallback;
import com.nickaknudson.mva.callbacks.ReadCallback;
import com.nickaknudson.mva.callbacks.UpdateCallback;

/**
 * @author nick
 *
 * @param <M>
 */
public interface CRUDClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param model
	 * @param callback
	 */
	public abstract void create(final M model, final CreateCallback<M> callback);
	/**
	 * @param model
	 * @param callback
	 */
	public abstract void read(final M model, final ReadCallback<M> callback);
	/**
	 * @param model
	 * @param callback
	 */
	public abstract void update(final M model, final UpdateCallback<M> callback);
	/**
	 * @param model
	 * @param callback
	 */
	public abstract void destroy(final M model, final DestroyCallback<M> callback);
}
