package com.nickaknudson.mva.callbacks;

/**
 * @author nick
 */
public interface UploadCallback extends ErrorCallback {
	/**
	 * @param result
	 */
	public void onUpload(String result);
}
