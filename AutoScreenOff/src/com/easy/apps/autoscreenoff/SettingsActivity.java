package com.easy.apps.autoscreenoff;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

public class SettingsActivity extends PreferenceActivity  implements OnPreferenceChangeListener,OnPreferenceClickListener {
	
	 CheckBoxPreference enableCallTimer;
	 CheckBoxPreference enableEndBtn;
	 CheckBoxPreference enableNotify;
	 CheckBoxPreference enableSearch;
	
	 public final static String KEY_CALL_TIMER = "enableTimer";
	 public final static String KEY_END = "enableEnd";
	 public final static String KEY_NOTIFY = "enableNotify";
	 public final static String KEY_SEARCH = "search";
	 public final static String KEY_CHOOSE= "chooseTime";
	 public final static String KEY_MORE_APP= "moreapp";
	 public final static String KEY_COLOR = "chooseColor";
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			 addPreferencesFromResource(R.xml.preferences);
			 
			 enableCallTimer = (CheckBoxPreference)findPreference(KEY_CALL_TIMER);
			 enableCallTimer.setOnPreferenceChangeListener(this);
			 
			 //enableEndBtn = (CheckBoxPreference)findPreference(KEY_END);
			 //enableEndBtn.setOnPreferenceChangeListener(this);
			 
			 enableNotify = (CheckBoxPreference)findPreference(KEY_NOTIFY);
			 enableNotify.setOnPreferenceChangeListener(this);
			 
			/* editMoreApp = (EditTextPreference)findPreference(KEY_MORE_APP);
			 
			 Intent intentPro = new Intent("android.intent.action.VIEW");
			 intentPro.setData(Uri.parse("market://details?id=com.easy.apps.networkflowwindows.pro"));
			 editMoreApp.setIntent(intentPro);*/
			 
			 if(this.getSharedPreferences("prefs", 0).getString(KEY_CHOOSE, "-1").equals("-1"))
				 setPrefsTime(KEY_CHOOSE,"1");	 
			 
			 if(this.getSharedPreferences("prefs", 0).getString(KEY_COLOR, "-1").equals("-1")){
				 setPrefsTime(KEY_COLOR,"#FFFF00");	 
				 Log.d("@@@", "2222222222222222");
				 
			 }else
				 Log.d("@@@", "hiiiiiiiiiiii");
			
			 if(enableNotify.isChecked()){
				 Log.d("@@@", "2222");
				 setPrefs(KEY_NOTIFY,true);
				 showGoogleSearchNotification();
			 }
			 if(enableCallTimer.isChecked()){
				 Log.d("@@@", "3333");
				 setPrefs(KEY_CALL_TIMER,true);
				 //Intent intent = new Intent(); intent.setClass(this, CaptureServices.class); this.startService(intent);
			 }
			 
			 
			 Log.d("@@@", String.valueOf(getPrefs(this,KEY_SEARCH)));
			 
			}catch(Exception e){
				e.printStackTrace();
			}
		
		Intent intent = new Intent();
		intent.setClass(this, SensorService.class);
		intent.setAction("com.firsttime");
		this.startService(intent);
	}
	
	private boolean getPrefs(Context context,String key){
	  	return context.getSharedPreferences("prefs", 0).getBoolean(key, false);
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		
		if(preference.getKey().equals(KEY_CALL_TIMER)){
		
			enableCallTimerTask();
		}else if(preference.getKey().equals(KEY_END)){
			
			//enableEndTask();
			
		}else if(preference.getKey().equals(KEY_NOTIFY)){
			
			enableNotifyTask();
		}else if(preference.getKey().equals(KEY_SEARCH)){
			
			enableSearchTask();
		}else{
			return false;
		}
		
		
			
		return true;
	}

	@Override
	public boolean onPreferenceClick(Preference arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.equals(KEY_CHOOSE)){
			Log.d("@@@","onPreferenceClick:" + KEY_CHOOSE);
		}
		
		return false;
	}
	
	private void enableCallTimerTask(){
		
		if(enableCallTimer.isChecked()){
			
			setPrefs(KEY_CALL_TIMER,false);
			
			Intent intent = new Intent();
			intent.setClass(this, SensorService.class);
			this.stopService(intent);

			
		}else{
			setPrefs(KEY_CALL_TIMER,true);
			
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			this.startActivity(intent);
			
			
			
			//Intent intent = new Intent(); intent.setClass(this, CaptureServices.class); this.startService(intent);
		}
	}
	
	private void enableEndTask(){
		Log.d("@@@", "enableEndTask");
		if(enableEndBtn.isChecked()){
			Log.d("@@@", "enableEndTask 1");
			setPrefs(KEY_END,false);
			
		}else{
			Log.d("@@@", "enableEndTask 2");
			setPrefs(KEY_END,true);
		}
	}
	
	private void enableSearchTask(){
		
		Log.d("@@@", "enableSearchTask");
		
		if(enableSearch.isChecked()){
			Log.d("@@@", "enableSearchTask 1");
			setPrefs(KEY_SEARCH,false);
			
		}else{
			Log.d("@@@", "enableSearchTask 2");
			setPrefs(KEY_SEARCH,true);
		}
	}
	
	private void enableNotifyTask(){
		
		if(enableNotify.isChecked()){
			
			setPrefs(KEY_NOTIFY,false);
			diableNotify();
			
		}else{
			setPrefs(KEY_NOTIFY,true);
			showGoogleSearchNotification();
		}
	}
	
	private void diableNotify(){
		NotificationManager notificationManager=(NotificationManager)this.getSystemService("notification");
		notificationManager.cancel(1);
	}
	private boolean setPrefs(String key, boolean flag)
	  {
	    SharedPreferences.Editor localEditor = this.getSharedPreferences("prefs", 0).edit();
	    localEditor.putBoolean(key, flag);
	    localEditor.commit();
	    return true;
	  }
		
		private boolean setPrefsTime(String key, String value)
	  {
	    SharedPreferences.Editor localEditor = this.getSharedPreferences("prefs", 0).edit();
	    localEditor.putString(key, value);
	    localEditor.commit();
	    return true;
	  }
		
		private void showGoogleSearchNotification(){
		  	
		  	NotificationManager notificationManager=(NotificationManager)this.getSystemService("notification");
		      //設定當按下這個通知之後要執行的activity

		      Intent notifyIntent = new Intent(); 
		      notifyIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
		      notifyIntent.setClass(this, SettingsActivity.class);
		      
		      String msg = this.getString(R.string.google_serach_notify);
		      
		      String finalMsg = this.getString(R.string.notify);
		      
		      PendingIntent appIntent=PendingIntent.getActivity(this,0,
		              notifyIntent,0);
		      
		      Notification notification = new Notification();
		      //設定出現在狀態列的圖示
		      notification.icon=R.drawable.ic_launcher;
		      
		      notification.flags = Notification.FLAG_NO_CLEAR;
		      //顯示在狀態列的文字
		      notification.tickerText= this.getString(R.string.google_serach_notify_title);
		      //會有通知預設的鈴聲、振動、light
		      notification.defaults|=Notification.DEFAULT_ALL;
		      
		      long[] vibrate = {100,500,100,1000}; 
		      notification.vibrate = vibrate;
		      
		      //notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
		      
		      /*notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		      
		      notification.defaults |= Notification.DEFAULT_VIBRATE;
		      
		      long[] vibrate = {0,100,200,300};
		      notification.vibrate = vibrate;
		      
		      notification.ledARGB = 0xff00ff00;
		      notification.ledOnMS = 300;
		      notification.ledOffMS = 1000;
		      notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		      */
		      //設定通知的標題、內容
		      notification.setLatestEventInfo(this,this.getString(R.string.app_name),finalMsg,appIntent);
		      //送出Notification
		      notificationManager.notify(1,notification);
		  }
}	
