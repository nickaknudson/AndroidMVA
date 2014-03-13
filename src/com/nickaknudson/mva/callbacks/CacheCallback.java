package com.nickaknudson.mva.callbacks;

import java.io.File;

/**
 * @author nick
 *
 */
public interface CacheCallback extends ErrorCallback {
	/**
	 * @param file
	 */
	public void onCache(File file);
}
