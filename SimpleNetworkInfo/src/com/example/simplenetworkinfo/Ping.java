package com.example.simplenetworkinfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.example.simplenetworkinfo.http.HttpFetch;
import com.example.simplenetworkinfo.utils.IpMacUtil;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 */
public class Ping extends BaseClass{

	Button pingerButton;
	int pstatuscode;
	long pduration;
	
	/*
	 * I need a context for a few different things.  It needs to be passed
	 * from here for now until I figure out how to properly do this.
	 * I have a few methods that need the context in order to work and I'm
	 * not sure the proper way to pass a context besides just declaring it and passing it down.
	 */
	Context context;
	boolean badaddr = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ping);
		context = this;
		
		/*
		 * Grab a reference to the button
		 * Set the onClickListener for that button
		 */
		pingerButton = (Button) findViewById(R.id.ping_button);
		pingerButton.setOnClickListener(pingClick);
		
		/*
		 * Display my statuscode chart. Its plain text.
		 * I need to pass an inputstream reader because the IS 
		 * needs a context and I cant pass it a context any other way 
		 * unless I declare the object here and overwrite the method. but
		 * This seems to be easier to just pass the IS. 
		 */
		InputStream is = context.getResources().openRawResource(R.raw.statuscodes);
		TextView tv = (TextView) findViewById(R.id.status_codes);
		try {
			tv.append("Status Codes: \n" + IpMacUtil.loadText(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The onClick listener for the ping button
	 * I create a new onclicklistner object and overwrite the onClick method
	 */
	Button.OnClickListener pingClick = new Button.OnClickListener(){
		public void onClick(View arg0){
			try {
				ping pinger = new ping();
				pinger.execute(0);  
			} catch (Exception e) {
				//Alert builder creates and alert
				AlertDialog.Builder alertDialogBuilder = 
						new AlertDialog.Builder(context).
						setMessage("Your shit is whack yo").
						setCancelable(false).
						setNeutralButton("Ok", null);
				//Set an alert to the builder.
				AlertDialog ad =  alertDialogBuilder.create();
				//Show the alert
				ad.show();
			}
		}
	};

	/**
	 * Seperate thread for network operations
	 * The InetAddress is what I pass.  
	 * The integer is the progress bar me thinks
	 * The bool is passed from the doInBackground to the postExecute
	 */
	class ping extends AsyncTask<Integer,Integer,Boolean> {

		//global
		TextView status = (TextView) findViewById(R.id.ping_status);

		protected Boolean doInBackground(Integer... params) {

			//Grab UI edit text
			EditText urlInEdit = (EditText) findViewById(R.id.ping_url);
			Editable urlInText = urlInEdit.getText();
			InetAddress ipAddress = null;
			String url = "";

			//Resolve the edit text to an ip address
			try {ipAddress = InetAddress.getByName(urlInText.toString());} 
			catch (UnknownHostException e) {badaddr = true;return false;}
			
			//Try catch to see if the hostname is an ipaddress or not
			try{url = ipAddress.getHostName();}
			catch(Exception e){url = urlInText.toString(); return false;}			

			/*
			 * declare an httpfecth object so I can override the onFetch
			 * I declare the onFetch so I can gain access to class variables
			 * and use them within this class.
			 */
			HttpFetch fetcher = new HttpFetch() {
				@Override
				protected void onFetch(long duration, int statuscode) {
					pstatuscode = statuscode;
					pduration = duration;
				}
			};

			//Call the fetch, which will in turn call the onfetch, giving me the code and duration
			fetcher.fetch("http://" + url);

			//if status 200 and duration under 500
			if(pstatuscode >= 200 && pduration <= 500){
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
			status.setText("Please Wait... \n \n \n");
		}

		//Outputs the results of the background thread to the UI
		protected void onPostExecute(Boolean result) {
			if(badaddr){
				//Alert builder creates and alert
				AlertDialog.Builder alertDialogBuilder = 
						new AlertDialog.Builder(context).
						setMessage("Invalid URL, IP or DNS lookup").
						setCancelable(false).
						setNeutralButton("Ok", null);
				//Set an alert to the builder.
				AlertDialog ad =  alertDialogBuilder.create();
				//Show the alert
				ad.show();
			}
			badaddr = false;
			if(result.booleanValue()){
				//Set output with duration and response. 
				status.setText("Response OK \nDuration: " + pduration + "\nStatuscode: " + pstatuscode + "\n");
			}else{
				status.setText("No response: Time out \n \n \n");
			}
		}
	}
}
