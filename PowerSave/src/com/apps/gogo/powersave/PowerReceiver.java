package com.apps.gogo.powersave;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class PowerReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String str = arg1.getAction();
		
		if(str != null){
			
			if(str.equals("android.intent.action.BATTERY_LOW")){
				setPowerSaverWork(arg0);
			}
		}
	}
	
	private void savaStatus2Prefs(Context conetxt){
		ContentResolver localContentResolver = conetxt.getContentResolver();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(conetxt);
		SharedPreferences.Editor editor = prefs.edit();
		
		 if (isLocationProviderEnable(localContentResolver, "gps")){
			 editor.putInt("enable_gps", 1);
		 }else
			 editor.putInt("enable_gps", 0);
		 
		 int j = Settings.Secure.getInt(localContentResolver, "assisted_gps_enabled", 2);
		 editor.putInt("enable_gps", j);
		 
		 int k = Settings.System.getInt(localContentResolver, "screen_brightness", 30);
		 editor.putInt("screen_brightness", k);
		 
		 int m = Settings.System.getInt(localContentResolver, "screen_off_timeout", 30000);
		 editor.putInt("screen_timeout", m);
		 
		 
		 int b = 0;
		try {
			b = Settings.System.getInt(localContentResolver, "screen_brightness_mode");
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     editor.putInt("state_of_auto_brightness_key",  b);
	        
	        editor.commit();
	}
	
	private void setPowerSaverWork(Context context){
		ContentResolver localContentResolver = context.getContentResolver();
		
		 Settings.Secure.putString(localContentResolver, "location_providers_allowed", "-" + "gps");
	 
	     Settings.Secure.putInt(localContentResolver, "assisted_gps_enabled", 0);
	     
	     Settings.System.putInt(localContentResolver, "screen_brightness", 30);
	     
	     Settings.System.putInt(localContentResolver, "screen_off_timeout", 3*1000);
	}
	
	  private static boolean isLocationProviderEnable(ContentResolver paramContentResolver, String paramString)
	  {
	    String str = Settings.Secure.getString(paramContentResolver, "location_providers_allowed");
	    return (str != null) && ((str.equals(paramString)) || (str.contains("," + paramString + ",")) || (str.startsWith(paramString + ",")) || (str.endsWith("," + paramString)));
	  }
	
}
