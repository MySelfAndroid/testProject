package com.easy.apps.flashcall;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.TelephonyManager;

public class Flash extends Service{
	private Camera.Parameters parameters;
	private Camera camera;
	private SurfaceTexture surfaceTexture;
	private Context context;
	private SharedPreferences setting;
	private TelephonyManager telephonyManager;
	private boolean isRun = true;
	
	private void stopFlash(){
	      isRun = false;
	}
	
	public void starFlash(){
		if (this.camera == null)
		    this.camera = Camera.open();
		if (this.parameters == null){
			
			this.parameters = this.camera.getParameters();
			List localList = this.parameters.getSupportedFlashModes();
			if (!"torch".equals(this.parameters.getFlashMode())){
				if (!localList.contains("torch"))
					this.parameters.setFlashMode("on");
				else
					this.parameters.setFlashMode("torch");
			}
			
		}
		
		try{
			this.camera.setPreviewTexture(this.surfaceTexture);
			final long now = System.currentTimeMillis();
			final int times = setting.getInt("times", 3);
			
			new Thread(){
				@Override
				public void run(){
					
					while(true){				
						 if (!isRun)
				               return;
						 
						 long after = System.currentTimeMillis();
						 if(after - now >= (times * 1000)){
							 releaseCamera();
							 mHandler.sendEmptyMessage(1);
							 break;
						 }
						 
						 mHandler.sendEmptyMessage(0);	 
						 int speed = setting.getInt("speed", 1000);						 
						 try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						mHandler.sendEmptyMessage(1);
						
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
			
			isRun = true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		this.context = getApplicationContext();
		this.setting = getSharedPreferences("Preference", 0);
		this.surfaceTexture = new SurfaceTexture(0);
		this.audioManager = ((AudioManager)getSystemService("audio"));
		
		this.starFlash();
		
		super.onCreate();
	}
	
	private AudioManager audioManager;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message message){
			switch(message.what){
				case 0:
					turnOnLight();
					//turnOffLight();				
					break;
				case 1:
					turnOffLight();
					break;
			}
		}
	};
	
	private void turnOnLight(){
		try{
			List localList = this.parameters.getSupportedFlashModes();
			if (!"torch".equals(this.parameters.getFlashMode())){
				if (!localList.contains("torch"))
					this.parameters.setFlashMode("on");
				else
					this.parameters.setFlashMode("torch");
			}
			this.parameters.setFocusMode("macro");
			this.camera.setParameters(this.parameters);
	        this.camera.startPreview();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void turnOffLight(){
		try{
			if(camera != null){
				parameters.setFlashMode("off");
				camera.setParameters(this.parameters);
				camera.startPreview();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void releaseCamera(){
		if (camera != null){
			camera.stopPreview();
		    camera.release();
		    camera = null;
		}
	}
}
