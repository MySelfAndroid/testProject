package com.easyway.apps.smartcallrecorder;

import java.util.Map;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class EzPhoneReceiver extends BroadcastReceiver{
	
	private static final String TAG = EzPhoneReceiver.class.getSimpleName();
	private static String incoming_number = null;
	private static boolean incomingFlag = false;
	private static int count = 0;
	private Context mContext;
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		mContext = context;
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			
			final String phoneNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			final long oldtime = System.currentTimeMillis();
			
			new Thread(){
				@Override
				public void run(){
					
					if(!Recorder.getOnUse()){
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Recorder.startRecording();
						Recorder.setOnUse(true);
						
						chkOutGoingCallEnd(oldtime,phoneNumber,context);
						
					}
				}
			}.start();
			
		}else{
			if(count == 4)
				count =0;
			
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			
			switch (tm.getCallState()) {
				case TelephonyManager.CALL_STATE_RINGING:
					
					incoming_number = intent.getStringExtra("incoming_number");
					Log.i(TAG, "RINGING :" + incoming_number);
					incomingFlag = true;
					
					break;
					
				case TelephonyManager.CALL_STATE_OFFHOOK:
					
					if (incomingFlag ) {
						count++;
						if(!Recorder.getOnUse() && count == 1){
							Recorder.startRecording();
							Recorder.setOnUse(true);
						}
					}
					
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					if (incomingFlag) {
						Log.d("@@@","idle count:"+ count);
						
						if(count == 1){
							count = 0;
							
							Recorder.stopRecording();
							Recorder.setOnUse(false);
							
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Map map = SaveRecorderFileInDB.queryLatestID(context);
							if(map !=null){
								
								String fileName = Recorder.getFlName(); 
								
								SaveRecorderFileInDB.saveFileNameInDB(context, fileName, incoming_number,(String)map.get("name"));
								
								showNotification(fileName);
							}
							
							
						}
					}
					break;
					
			}
			
		}
	}
	
	private void chkOutGoingCallEnd(long olddate,String number,Context context){
		//Log.d(TAG, "chkOutGoingCallEnd...");
		try{
			
			long newTime = 0L;
			
			while(true){
				
				Thread.sleep(1000);
				
				Map map = SaveRecorderFileInDB.queryLatestNumber(context);
				
				String oldNumber = (String)map.get("number");
				String date = (String)map.get("date");
				String dur = (String)map.get("dur");
				String name = (String)map.get("name");
				
				long durDate = Long.parseLong(dur);
				
				newTime = System.currentTimeMillis();
 				
				if(newTime - olddate >= 20 * 60 * 1000){
					Log.d(TAG, "time out");
					break;
				}
				
				long finDate = Long.parseLong(date);
				
				long goodDate = durDate + finDate;
				
				
				Log.d("@@@","compare:"+ olddate +","+goodDate +"," +(olddate - goodDate));
				
				
				if(oldNumber.equals(number) && (((olddate - goodDate)  <= 0))){
					
					Log.d("@@@","match end number");
					
					long diff = newTime - olddate;
				
					Recorder.stopRecording();
					Recorder.setOnUse(false);
					
					String fileName = Recorder.getFlName(); 
					
					Log.d("@@@","Recorder.getFlName2:"+ fileName +","+number);
					
					SaveRecorderFileInDB.saveFileNameInDB(context, fileName, number,name);
					
					showNotification(fileName);
					
					break;
				}
	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void showNotification(String fileName){
	  	
	  	NotificationManager notificationManager=(NotificationManager)mContext.getSystemService("notification");
	      //設定當按下這個通知之後要執行的activity

	      Intent notifyIntent = new Intent(); 
	      notifyIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      notifyIntent.setClass(mContext, MainActivity.class);
	      notifyIntent.putExtra("action", fileName);
	      String finalMsg = mContext.getString(R.string.notify);
	      
	      PendingIntent appIntent=PendingIntent.getActivity(mContext,0,
	              notifyIntent,PendingIntent.FLAG_CANCEL_CURRENT);
	      
	      Notification notification = new Notification();
	      //設定出現在狀態列的圖示
	      notification.icon=R.drawable.notification;
	      
	      notification.flags = Notification.FLAG_AUTO_CANCEL;
	      //顯示在狀態列的文字
	      notification.tickerText= mContext.getString(R.string.keep);
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
	      notification.setLatestEventInfo(mContext,mContext.getString(R.string.app_name),finalMsg,appIntent);
	      //送出Notification
	      notificationManager.notify(new Random().nextInt(50000),notification);
	  }
}
