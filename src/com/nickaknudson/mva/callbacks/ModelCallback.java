package com.nickaknudson.mva.callbacks;

import com.nickaknudson.mva.Model;

public interface ModelCallback<T extends Model<T>> {
	public void onModel(T model);
}
