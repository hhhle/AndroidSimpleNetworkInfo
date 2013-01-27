package com.example.simplenetworkinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseClass {

	// globals
	ConnectivityManager connMgr;
	NetworkInfo wifiInfo, mobileInfo, networkInfo;
	Button buttonCheckConn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//grab the button reference
		buttonCheckConn = (Button) findViewById(R.id.button_check_conn);
		//set the onclick listerner.  Like in C where u declare the function, then define it later
		buttonCheckConn.setOnClickListener(checkConnListener);
		//lazy work around until i get seperate thread
		//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		//StrictMode.setThreadPolicy(policy);
	}

	/*
	 * The onClick listener.  I am writing a new onClickListener class and then overwriting the 
	 * onCLick method.  I have no idea what the beneifits of this over using the onClick is.  
	 */
	Button.OnClickListener checkConnListener = new Button.OnClickListener() {
		public void onClick(View arg0) {
			// create a text view that will change based on whether or not connected
			TextView mConnection_Request = (TextView) findViewById(R.id.connection_status);
			//textview to hold the network ifo toString()
			TextView t2 = (TextView)findViewById(R.id.networkInfo);
			//grab network objects
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			mobileInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			//if connected display information
			if (networkInfo != null && networkInfo.isConnected()) {
				// assign the text view connected.
				mConnection_Request.setText("Connected");
				//Gets info on current connection
				DhcpInfo DhcpNetInfo = new DhcpInfo();
				TextView t1 = (TextView)findViewById(R.id.IPaddress);
				t1.setText(DhcpNetInfo.toString());
				//append it into a text view
				//Set text to blank so that consecutive button presses doesnt keep appeninding.
				t2.setText("");
				String s1 = networkInfo.toString();
				String s2 = wifiInfo.toString();
				String s3 = mobileInfo.toString();
				t2.append("\n" + s1 + "\n \n" + s2 + "\n \n" + s3 + "\n \n");
			} else {
				// assign text view as not connected
				mConnection_Request.setText("Not Connected");
			}
		}
	};
}