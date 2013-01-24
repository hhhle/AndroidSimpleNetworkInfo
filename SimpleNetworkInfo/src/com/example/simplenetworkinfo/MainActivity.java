package com.example.simplenetworkinfo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	// globals
	ConnectivityManager connMgr;
	NetworkInfo wifiInfo, mobileInfo, networkInfo;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//lazy work around until i get seperate thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	/*
	 * Writes to textView connection_status if isConnected
	 * @ Param View 
	 */
	public void sendRequest(View view) {
		// create a text view that will change based on whether or not connected
		TextView mConnection_Request = (TextView) findViewById(R.id.connection_status);
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// assign the text view connected.
			mConnection_Request.setText("Connected");
		} else {
			// assign text view as not connected
			mConnection_Request.setText("Not Connected");
		}
		
		//Gets info on current connection
		DhcpInfo DhcpNetInfo = new DhcpInfo();
		TextView t1 = (TextView)findViewById(R.id.IPaddress);
		t1.setText(DhcpNetInfo.toString());
		
		//Get current network info
		//ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		//networkInfo = connMgr.getActiveNetworkInfo();
		wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    mobileInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
	    //append it into a text view
	    TextView t2 = (TextView)findViewById(R.id.networkInfo);
	    //Set text to blank so that consecutive button presses doesnt keep appeninding.
	    t2.setText("");
	    String s1 = networkInfo.toString();
	    String s2 = wifiInfo.toString();
	    String s3 = mobileInfo.toString();
	    t2.append("\n" + s1 + "\n \n" + s2 + "\n \n" + s3 + "\n \n");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}