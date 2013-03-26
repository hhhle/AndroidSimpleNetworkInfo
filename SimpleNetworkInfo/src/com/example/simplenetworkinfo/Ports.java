package com.example.simplenetworkinfo;

/*
 * 	*PORT 80 - HTTP
 *PORT 443 - HTTPS
 *port 22 - ssh
 *socket mySocket= new Socket()
 *Socket(InetAddress dstAddress, int dstPort)
 *        Creates a new streaming socket connected to the target host 
 *        specified by the parameters dstAddress and dstPort.
 *        
 * mySocket.connect(new InetSocketAddress(targetAddress, i), 1000);
 * i = the port
 * target address = the ipaddress of where you are trying to connect.
 * The int is the timeout
 * If it doesnt connect it throws an excpetion, so make a try catch.
 * if it doesnt throw then its connect and after the .connect say port is listening, close port afterwards
 * if it throws then it didnt connect and have the catch say the port is closed
 * 
 *
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class Ports extends BaseClass{

	String targetHost = "173.194.78.106";
	String targetHostName;
	boolean stop = false;
	String results;
	public int startPort = 75;
	public int endPort = 85;
	InetAddress targetAddress;
	TextView scanText;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_port);

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

	private void prepareScan(){
			try{targetAddress = InetAddress.getByName(targetHost);}
			catch(UnknownHostException e){e.printStackTrace();}
			try{targetHostName = targetAddress.getHostName();}
			catch(Exception e){targetHostName = targetHost;}
	}


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
				break;
			}
		}
	}

	public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	scanText.append(results);
        }
    };

}
