package com.example.simplenetworkinfo;

import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BaseTab extends TabActivity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void goToMenu1() {
		Intent intent = new Intent(this, NetWorkInfo.class);
		startActivity(intent);
	}
	
	public void goToMenu2() {
		Intent intent = new Intent(this, Ping.class);
		startActivity(intent);
	}
	
	public void goToMenu3() {
		Intent intent = new Intent(this, Ports.class);
		startActivity(intent);
	}
	
	public void goToSettings() {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.button_1:
			goToMenu1();
			return true;
		case R.id.button_2:
			goToMenu2();
			return true;
		case R.id.button_3:
			goToMenu3();
			return true;
		case R.id.settings:
			goToSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
