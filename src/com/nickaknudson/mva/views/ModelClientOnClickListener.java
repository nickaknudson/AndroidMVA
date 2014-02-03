package com.nickaknudson.mva.views;

import android.view.View;

import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.clients.Client;

/**
 * @author nick
 *
 * @param <M>
 * @param <C>
 */
public abstract class ModelClientOnClickListener<M extends Model<M>, C extends Client> extends ModelOnClickListener<M> {

	private C client;

	/**
	 * @param model
	 * @param client
	 */
	public ModelClientOnClickListener(M model, C client) {
		super(model);
		this.client = client;
	}

	@Override
	public void onClick(View v, M model) {
		onClick(v, model, client);
	}
	
	/**
	 * @param v
	 * @param model
	 * @param client
	 */
	public abstract void onClick(View v, M model, C client);
}
