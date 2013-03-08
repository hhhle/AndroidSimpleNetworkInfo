package com.example.simplenetworkinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.example.simplenetworkinfo.tab.*;

public class NetWorkInfo extends BaseTab {

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Current")
                .setContent(new Intent(this, CurrentConn.class)));

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Mobile")
                .setContent(new Intent(this, MobileConn.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Wifi")
                .setContent(new Intent(this, WifiConn.class)));
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("Bluetooth")
                .setContent(new Intent(this, BluetoothConn.class)));
    }
}