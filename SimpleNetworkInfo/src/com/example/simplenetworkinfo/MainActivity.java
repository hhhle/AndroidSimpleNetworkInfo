package com.example.simplenetworkinfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
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
		
		//Gets info on current connection
		DhcpInfo DhcpNetInfo = new DhcpInfo();
		TextView t1 = (TextView)findViewById(R.id.IPaddress);
		t1.setText(DhcpNetInfo.toString());
		
		//Get current network info
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connMgr.getActiveNetworkInfo();
		wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    mobileInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
	    //append it into a text view
	    TextView t2 = (TextView)findViewById(R.id.networkInfo);
	    String s1 = networkInfo.toString();
	    String s2 = wifiInfo.toString();
	    String s3 = mobileInfo.toString();
	    t2.append("\n \n" + s1 + "\n \n" + s2 + "\n \n" + s3 + "\n \n");
	    
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
	}
	
	/**
	 * @param view
	 * @throws Exception
	 */
	public void pingWrapper(View view) throws Exception{
		
		//ui
	    EditText urlInEdit = (EditText) findViewById(R.id.ping_url);
	    Editable urlInText = urlInEdit.getText();
	    
	    //net
	    //network classes
	    InetAddress ipAddress = null;
    	//Grabs the ipaddress based on a name
        ipAddress = InetAddress.getByName(urlInText.toString());
	    
    	//Grabs the ipaddress based on a name
        try {
			ipAddress = InetAddress.getByName(urlInText.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        //calls the background thread
		ping pinger = new ping();
		pinger.execute(ipAddress);     
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

/*
 * Seperate thread for network operations
 * ***FOR SOME REASON IT MAKES ME USE AN ARRAY OF BOOLS.***
 */
class ping extends AsyncTask<InetAddress, Integer, Boolean> {
	
	//global
	TextView status = (TextView) findViewById(R.id.ping_status);


	protected Boolean doInBackground(InetAddress... params) {
		InetAddress ipAddress = params[0];
    	//pings the ipaddress 
        try {
			if (ipAddress.isReachable(5000)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return false;
	}
	
	//Wrapper for the progress percent
	protected void onProgressUpdate(Integer... progress) {
		setProgressPercent(progress[0]);
	}

	//do work here for displaying progress
	private void setProgressPercent(Integer integer) {
	}

	//gets executed before the background thread. Initial UI work
	protected void onPreExecute() {
		status.setText("Please Wait...");
	}

	//Outputs the results of the background thread to the UI
	protected void onPostExecute(Boolean result) {
		
		if(result.booleanValue()){
		status.setText("Response OK");
		}else{
		status.setText("No response: Time out");
		}
	}
}
}