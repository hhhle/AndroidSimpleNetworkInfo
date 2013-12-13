package com.example.simplenetworkinfo;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.suitebuilder.annotation.Suppress;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Scans port of a specified website.  
 * 
 * @author mstanford
 *
 */
public class Ports extends BaseClass{

	String targetHost = "google.com";
	String targetHostName;
	boolean stop = false;
	String results;
	public int startPort = 10;
	public int endPort = 25;
	InetAddress targetAddress;
	TextView scanText;
	Button btn_start,btn_stop;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_port);

		//Grab the references to the buttons
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_stop = (Button) findViewById(R.id.btn_stop);
		//Set listeners
		btn_start.setOnClickListener(btnStart);
	}

	/**
	 * Starts the scan
	 */
	Button.OnClickListener btnStart = new Button.OnClickListener() {
		public void onClick(View v){
			
			EditText editText = (EditText) findViewById(R.id.et_portAddress);
			targetHost = editText.getText().toString();
			
			EditText begin = (EditText) findViewById(R.id.et_beginPort);
			startPort = Integer.parseInt(begin.getText().toString());
			EditText end = (EditText) findViewById(R.id.et_endPort);
			endPort = Integer.parseInt(end.getText().toString());
			
			if(endPort < startPort) endPort = startPort;
			
			/* Textview which displays the scanresult */
			scanText = (TextView) findViewById(R.id.tv_portResults);
			scanText.setText("Scanning host "+ targetHost + "\n");
			
			new Thread(){
				@Override
				public void run() {
					prepareScan();
					startScan();
				}
			}.start();
		}
	};
	
	/**
	 * Sets the stop varibale to true, which will stop the scanner.
	 */
	Button.OnClickListener btnStop = new OnClickListener(){

		@Override
		public void onClick(View v) {
			stop = true;
		}
		
	};
	
		/**
		 * Prepares scan by resolving the string into an address.
		 * Grabs the URL of the address.  
		 */
		private void prepareScan(){
			try{targetAddress = InetAddress.getByName(targetHost);}
			catch(UnknownHostException e){e.printStackTrace();}
			try{targetHostName = targetAddress.getHostName();}
			catch(Exception e){targetHostName = targetHost;}
		}


		/**
		 * Port scans the specified target on the specified port range.
		 */
		private void startScan()
		{
			/* Socket to connect to the remote machine */
			Socket portSocket;

			for (int i = startPort; i <= endPort; i++)
			{
				if(!stop){
					try
					{
						portSocket = new Socket();
						portSocket.connect(new InetSocketAddress(targetAddress, i), 1000);
						results = "Target is listening on port "+ i + ": Port Open\n";
						portSocket.close();
						handler.sendEmptyMessage(0);
					}   
					catch(Exception exception){
						results = "Target is not listening on port "+ i + ": Port Closed\n";
						handler.sendEmptyMessage(0);
					}
				}else {
					stop = false;
					break;
				}
			}
		}

		/**
		 * Grabs handler messages and updates the UI
		 */
		public Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				scanText.append(results);
			}
		};

	}
