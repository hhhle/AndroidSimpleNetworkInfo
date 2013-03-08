package com.example.simplenetworkinfo.tab;

import com.example.simplenetworkinfo.R;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class BaseConn extends Activity {
	
	// globals
	ConnectivityManager connMgr;
	NetworkInfo conn;
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
		displayInfo();
		buttonCheckConn.setText("Refresh");
	}

	Button.OnClickListener checkConnListener = new Button.OnClickListener() {
		public void onClick(View arg0) {
			//Change to refresh
			buttonCheckConn.setText("Refresh");
			displayInfo();
		}
	};
	
	public void displayInfo(){
	}
}
