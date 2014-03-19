package com.easy.apps.autoscreenoff;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private DevicePolicyManager deviceManager;
	private ComponentName mDeviceAdmin;
	
	private boolean isActiveAdmin(){
	    return this.deviceManager.isAdminActive(this.mDeviceAdmin);
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.deviceManager = ((DevicePolicyManager)getSystemService("device_policy"));
	    this.mDeviceAdmin = new ComponentName(this, AdminReceiver.class);
	    
	    if (!isActiveAdmin())
	        sendDeviceAdminIntent();
	    else{
	    	
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void sendDeviceAdminIntent() {
		Intent localIntent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
		localIntent.putExtra("android.app.extra.DEVICE_ADMIN",
				this.mDeviceAdmin);
		localIntent.putExtra("android.app.extra.ADD_EXPLANATION",
				getResources().getString(R.string.extend));
		startActivityForResult(localIntent, 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (resultCode != -1)
				Log.d("@@@", "admin ok");
		}
		
		this.finish();
	}

}
