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

public class Ports extends BaseClass{

}
