package com.easy.apps.flashcall;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

public class NotificationSetting {
	
	private static Context mContext;
	private static final int NOTIFY_ID_LED = 2;
	
	public static void setContext(Context context){
		mContext = context;
	}
	
	public void doNotifyLED(){
		
		String color = mContext.getSharedPreferences("prefs", 0).getString("color", "#FF0000");
		String inter = mContext.getSharedPreferences("prefs", 0).getString("inter", "500");
		
		Log.d("@@@", "doNotifyLED");
		Notification notification = new Notification();  
		notification.ledARGB =0xff00ff00;
		notification.ledOnMS = Integer.parseInt(inter); 
		notification.ledOffMS = Integer.parseInt(inter); 
		notification.flags = Notification.FLAG_SHOW_LIGHTS;
		
		NotificationManager mNoticationManager = (NotificationManager)mContext.getSystemService("notification");
		mNoticationManager.notify(NOTIFY_ID_LED, notification);  
	}
	
	public void stopNotifyLED(){
		NotificationManager mNoticationManager = (NotificationManager)mContext.getSystemService("notification");
		mNoticationManager.cancel(NOTIFY_ID_LED);  
	}
	
}
