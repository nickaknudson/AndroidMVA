package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.UploadCallback;

/**
 * @author nick
 *
 * @param <M> model type
 */
public interface UploadClient<M extends Model<M>> extends ModelClient<M> {
	/**
	 * @param model 
	 * @param callback
	 */
	public abstract void upload(final M model, final UploadCallback callback);
}
