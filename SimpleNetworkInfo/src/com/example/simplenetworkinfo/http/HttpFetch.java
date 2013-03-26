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

	int statuscode;
	long duration;
	
	/**
	 * Called whenever the fetch is made.
	 * 
	 * @param response
	 * @param statuscode
	 */
	protected void onFetch(long duration, int statuscode) {

	}

	public void fetch(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = null;
		try {
			request = new HttpGet(url);
		} catch (IllegalArgumentException  e) {
		}
		try {
			long start = System.currentTimeMillis();
			HttpResponse response = client.execute(request);
			//http://en.wikipedia.org/wiki/List_of_HTTP_status_codes
			statuscode = response.getStatusLine().getStatusCode();
			duration = System.currentTimeMillis() - start;
			onFetch(duration,statuscode);
		} catch (IOException ex) {
		}
	}
}
