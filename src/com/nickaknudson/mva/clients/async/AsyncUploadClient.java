package com.nickaknudson.mva.clients.async;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.nickaknudson.mva.Model;
import com.nickaknudson.mva.callbacks.UploadCallback;
import com.nickaknudson.mva.clients.UploadClient;

/**
 * @author nick
 *
 * @param <M>
 */
public abstract class AsyncUploadClient<M extends Model<M>> implements UploadClient<M> {

	/**
	 * @param url
	 * @param body
	 * @param model
	 * @param callback
	 */
	public void upload(String url, MultipartFormDataBody body, M model, final UploadCallback callback) {
		AsyncHttpPost post = new AsyncHttpPost(url);
		post.setBody(body);
		AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {
			@Override
			public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
				if(e != null) {
					if(callback != null) callback.onError(e);
				} else {
					if(callback != null) callback.onUpload(result);
				}
			}
		});
	}

}
