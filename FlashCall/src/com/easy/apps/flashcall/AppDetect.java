package com.easy.apps.flashcall;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AppDetect extends AccessibilityService{
	private static boolean isInit = false;
	private String [] igroneArrary = {"android.widegt.Toast","flashcall"};
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent arg0) {
		// TODO Auto-generated method stub
		try{
			String pkgName = arg0.getClassName().toString();
			//Log.d("@@@", "onAccessibilityEvent:"+ pkgName);
			
			if(arg0.getClassName().toString().indexOf("Noti") != -1){
				Log.d("@@@", "onAccessibilityEvent:"+ arg0.getPackageName().toString());
				
				for(int i=0;i<igroneArrary.length;i++){
					if(pkgName.contains(igroneArrary[i]))
						return;
				}
				
				if(checkIsInAppList(arg0.getPackageName().toString())){
					final NotificationSetting ns = new NotificationSetting();
					//Thread.sleep(3000);
					ns.doNotifyLED();
					
					new Thread(){
						@Override
						public void run(){
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ns.stopNotifyLED();
						}
					}.start();
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		 
	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		isInit = false;
		Log.v("@@@", "***** onInterrupt");
	}
	
	private boolean checkIsInAppList(String pkgName){
		return true;
	}
	
	@Override
	protected void onServiceConnected() {
	    /*if (isInit) {
	        return;
	    }*/
		
		Log.v("@@@", "***** onServiceConnected");
	    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
	    info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
	    info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
	    info.notificationTimeout = 100L;
	    setServiceInfo(info);
	    
	    isInit = true;
	}
}
