package com.example.simplenetworkinfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import com.example.simplenetworkinfo.http.HttpFetch;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
	Context context;
	boolean badaddr = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// call to super
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ping);
		context = this;
		/**
		 * Grab a reference to the button
		 * Set the onClickListener for that button
		 */
		pingerButton = (Button) findViewById(R.id.ping_button);
		pingerButton.setOnClickListener(pingClick);
	}

	/**
	 * The onClick listener for the ping button
	 * I create a new onclicklistner object and overwrite the onClick method
	 */
	Button.OnClickListener pingClick = new Button.OnClickListener(){
		public void onClick(View arg0){
			try {
				pingWrapper(arg0);
			} catch (Exception e) {
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
			//Creat address array, needed to get it to work, wont work without an array
			InetAddress [] ipAddress = null;

			//Try catch block for the getallbyname method
			try {
				ipAddress = InetAddress.getAllByName(urlInText.toString());
				//if it catches an exception then create an alert saying that
			} catch (UnknownHostException e) {
				//Return false to signal a time out or bad address
				badaddr = true;
				return false;
			}

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
			fetcher.fetch("http://www." + ipAddress[0].getHostName());

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
			status.setText("Please Wait...");
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
			if(result.booleanValue()){
				status.setText("Response OK \nDuration: " + pduration + "\nStatuscode: " + pstatuscode);
			}else{
				status.setText("No response: Time out");
			}
		}
	}
}
