package com.example.simplenetworkinfo.tab;

import com.example.simplenetworkinfo.R;
import com.example.simplenetworkinfo.utils.IpMacUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.TextView;

public class MobileConn extends BaseConn{

	@Override
	public void displayInfo() {
		// create a text view that will change based on whether or not connected
		TextView tv_status = (TextView) findViewById(R.id.connection_status);
		//textview to hold the network ifo toString()
		TextView tv_info = (TextView)findViewById(R.id.networkInfo);
		//grab network objects
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		conn = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		//if connected display information
		if (conn != null) {
			tv_info.setText("");
			tv_info.append("\nMobile: \n" + conn.toString());
		} 
		if (conn.isConnected()) {
			tv_status.setText("Connected! \n");
		}else{
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
