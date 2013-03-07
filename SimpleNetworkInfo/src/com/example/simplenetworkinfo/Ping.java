package com.example.simplenetworkinfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import com.example.simplenetworkinfo.http.HttpFetch;

import android.net.Uri;
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
	int pstatuscode;
	long pduration;
	
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
        //calls the background thread
		ping pinger = new ping();
		//I have to supply a parameter. I used to have address but it needed to be in the Async
		pinger.execute(5);     
	}

/*
 * Seperate thread for network operations
 * The InetAddress is what I pass.  
 * The integer is the progress bar me thinks
 * The bool is passed from the doInBackground to the postExecute
 */
class ping extends AsyncTask<Integer,Integer,Boolean> {
	
	//global
	TextView status = (TextView) findViewById(R.id.ping_status);

	protected Boolean doInBackground(Integer... params) {
		
		
		
		//ui
	    EditText urlInEdit = (EditText) findViewById(R.id.ping_url);
	    Editable urlInText = urlInEdit.getText();

	    /*
	     * for some strange strange reason using an array of addresses fixes my problem.
	     */
	    InetAddress [] ipAddress = null;

    	//Grabs the ipaddress based on a name
        try {
        	/*
        	 * For some reason calling getAll doesn't crash the app but still doesn't work
        	 * There is an issue in the emulator with DNS lookup
        	 * To bypass this issue for some reason you can use an array of addresses and it 
        	 * doesn't crash the app.  Hopefully in a future update,
        	 * google will fix the DNS in the emulator
        	 */
        	ipAddress = InetAddress.getAllByName(urlInText.toString());
        	Log.d("Address",ipAddress[0].getHostAddress());
        	Log.d("Address",ipAddress[0].getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    
	    
        HttpFetch fetcher = new HttpFetch() {
            @Override
            protected void onFetch(long duration, int statuscode) {
            	pstatuscode = statuscode;
            	pduration = duration;
            }
        };
                
        fetcher.fetch("http://www." + ipAddress[0].getHostName());
        
    	if(pstatuscode >= 200){
    		return true;
    	}else{
    		return false;
   	}

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
		status.setText("Response OK \nDuration: " + pduration + "\nStatuscode: " + pstatuscode);
		}else{
		status.setText("No response: Time out");
		}
	}
}
}
