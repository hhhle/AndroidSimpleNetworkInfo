package com.example.simplenetworkinfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.app.Activity;
import android.os.AsyncTask;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Ping extends Activity{
	
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