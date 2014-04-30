package com.easyway.apps.smartcallrecorder;

import java.util.Map;

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
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		
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
							
							Map map = SaveRecorderFileInDB.queryLatestID(context);
							if(map !=null){
								
								String fileName = Recorder.getFlName(); 
								
								SaveRecorderFileInDB.saveFileNameInDB(context, fileName, (String)map.get("date"),(String)map.get("name"));
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
					
					break;
				}
	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
