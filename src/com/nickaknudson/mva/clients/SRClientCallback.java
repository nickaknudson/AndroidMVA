package com.nickaknudson.mva.clients;

import com.nickaknudson.mva.Model;

public interface SRClientCallback<T extends Model> {
	public void onRecieve(T model);
}
