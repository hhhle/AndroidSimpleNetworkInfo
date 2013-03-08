package com.example.simplenetworkinfo.tab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.TextView;

import com.example.simplenetworkinfo.R;
import com.example.simplenetworkinfo.utils.IpMacUtil;

public class CurrentConn extends BaseConn {
	
	public void displayInfo(){
		TextView tv_status = (TextView) findViewById(R.id.connection_status);
		//textview to hold the network ifo toString()
		TextView tv_info = (TextView)findViewById(R.id.networkInfo);
		//grab network objects
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		conn = connMgr.getActiveNetworkInfo();
		
		if(conn.isConnected()){
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
				conn.toString());
	}
}
