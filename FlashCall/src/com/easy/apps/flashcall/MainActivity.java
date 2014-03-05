package com.easy.apps.flashcall;

import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private String LOGTAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Intent intent = new Intent();
		intent.setClass(this, Flash.class);
		this.startService(intent);*/
		
		Log.d(LOGTAG, "enable:" + isAccessibilityEnabled());
		
		NotificationSetting.setContext(this);
	
		
	}
	
	private boolean isAccessibilityEnabled(){
		int accessibilityEnabled = 0;
	    final String MY_ACCESSIBILITY_SERVICE = "com.easy.apps.flashcall/com.easy.apps.flashcall.AppDetect";
	    boolean accessibilityFound = false;
	    
	    try {
	        accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(),android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
	        Log.d(LOGTAG, "ACCESSIBILITY: " + accessibilityEnabled);
	    } catch (SettingNotFoundException e) {
	        Log.d(LOGTAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
	    }

	    TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

	    if (accessibilityEnabled==1){
	        Log.d(LOGTAG, "***ACCESSIBILIY IS ENABLED***: ");


	         String settingValue = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
	         Log.d(LOGTAG, "Setting: " + settingValue);
	         if (settingValue != null) {
	             TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
	             splitter.setString(settingValue);
	             while (splitter.hasNext()) {
	                 String accessabilityService = splitter.next();
	                 Log.d(LOGTAG, "Setting: " + accessabilityService);
	                 if (accessabilityService.equalsIgnoreCase(MY_ACCESSIBILITY_SERVICE)){
	                     Log.d(LOGTAG, "We've found the correct setting - accessibility is switched on!");
	                     return true;
	                 }
	             }
	         }

	        Log.d(LOGTAG, "***END***");
	    }
	    else{
	        Log.d(LOGTAG, "***ACCESSIBILIY IS DISABLED***");
	    }
	    return accessibilityFound;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
