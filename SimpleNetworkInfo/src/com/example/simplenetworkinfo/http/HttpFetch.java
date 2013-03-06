package com.example.simplenetworkinfo.http;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Fetches an HTTP call.
 * 
 * @author rschilling
 */
public class HttpFetch {

	public static final boolean DO_LOG = true;

	public static final String LOG_TAG = "HttpFetch";

	/**
	 * Called whenever the fetch is made.
	 * 
	 * @param response
	 * @param statuscode
	 */
	protected void onFetch(HttpResponse response, int statuscode) {

	}

	public void fetch(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			long start = System.currentTimeMillis();
			HttpResponse response = client.execute(request);
			int statuscode = response.getStatusLine().getStatusCode();
			long duration = System.currentTimeMillis() - start;
		} catch (IOException ex) {
			
		}
	}

}
