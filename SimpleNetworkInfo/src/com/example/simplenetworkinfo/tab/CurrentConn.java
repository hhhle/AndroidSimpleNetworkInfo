package com.example.simplenetworkinfo.tab;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.simplenetworkinfo.R;
import com.example.simplenetworkinfo.utils.IpMacUtil;

public class CurrentConn extends Activity {
	
	// globals
	ConnectivityManager connMgr;
	NetworkInfo currentConn;
	Button buttonCheckConn;
	ViewFlipper vf_networkInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//grab the button reference
		buttonCheckConn = (Button) findViewById(R.id.button_check_conn);
		//set the onclick listerner.
		buttonCheckConn.setOnClickListener(checkConnListener);
	}

	Button.OnClickListener checkConnListener = new Button.OnClickListener() {
		public void onClick(View arg0) {
			//Change to refresh
			buttonCheckConn.setText("Refresh");
			// create a text view that will change based on whether or not connected
			TextView tv_status = (TextView) findViewById(R.id.connection_status);
			//textview to hold the network ifo toString()
			TextView tv_info = (TextView)findViewById(R.id.networkInfo);
			//grab network objects
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			currentConn = connMgr.getActiveNetworkInfo();
			
			if(currentConn.isConnected()){
				tv_status.setText("Connected! \n");
			}else {
				tv_status.setText("Not Connected! \n");
			}

			tv_info.setText(
					"MAC: " +
					IpMacUtil.getMACAddress("wlan0") + "\n" +
					"IPv4: " + 
					IpMacUtil.getIPAddress(true) + "\n" +
					"IPv6: " + 
					IpMacUtil.getIPAddress(false) + "\n \n" +
					currentConn.toString());
		
		}
	};
}
