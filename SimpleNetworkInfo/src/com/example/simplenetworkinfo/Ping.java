package com.example.simplenetworkinfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Pings an address.  Uses the isReachable function.
 */
public class Ping extends BaseClass{
	
	Button pingerButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ping);
		
		/*
		 * Grab a reference to the button
		 * Set the onClickListener for that button
		 */
		pingerButton = (Button) findViewById(R.id.ping_button);
		pingerButton.setOnClickListener(pingClick);
	}
	
	/*
	 * The onClick listener for the ping button
	 * I create a new onclicklistner object and overwrite the onClick method
	 */
	Button.OnClickListener pingClick = new Button.OnClickListener(){
		public void onClick(View arg0){
			try {
				pingWrapper(arg0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	/**
	 * Wraps the async task.  Is called onClick of ping button
	 * @param view
	 * @throws Exception
	 */
	public void pingWrapper(View view) throws Exception{
		
		//ui
	    EditText urlInEdit = (EditText) findViewById(R.id.ping_url);
	    Editable urlInText = urlInEdit.getText();
	    
	    //network classes
	    InetAddress ipAddress = null;

		Log.d("Grabbed IP",urlInText.toString());
	    
    	//Grabs the ipaddress based on a name
        try {
    		Log.d("trying to get to ip",urlInText.toString());
        	//HERES THE ERROR!!!!
    		//I think the error is based on a bad DNS lookup.  
			ipAddress = InetAddress.getByName(urlInText.toString());
			//ERROR ^^^^
			Log.d("Check IP",ipAddress.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        //calls the background thread
		ping pinger = new ping();
		pinger.execute(ipAddress);     
	}

/*
 * Seperate thread for network operations
 * The InetAddress is what I pass.  
 * The integer is the progress bar me thinks
 * The bool is passed from the doInBackground to the postExecute
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
	
	//Wrapper for the progress percent. Updates the progress.
	protected void onProgressUpdate(Integer... progress) {
		setProgressPercent(progress[0]);
	}

	//do work here for displaying progress.  Called by the update function.
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
