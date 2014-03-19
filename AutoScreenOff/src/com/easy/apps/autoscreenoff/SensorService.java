package com.easy.apps.autoscreenoff;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.view.OrientationEventListener;

public class SensorService extends Service implements SensorEventListener{
	
	ComponentName mDeviceAdmin;
	OrientationEventListener mOrientationListener;
	private Sensor mProximity;
	DevicePolicyManager deviceManager;
	private SensorManager mSensorManager;
	private boolean isRegisterSensor = false;
	private float nowValue;
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.sensor.getType() == 8) {
			float f = arg0.values[0];

			if ((f - this.nowValue < 0.5D) || (f - this.nowValue > -0.5D)) {
				this.nowValue = f;
				this.deviceManager.lockNow();
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		super.onCreate();
		
		this.deviceManager = ((DevicePolicyManager)getSystemService("device_policy"));
		this.mSensorManager = ((SensorManager)getSystemService("sensor"));
		this.mProximity = this.mSensorManager.getDefaultSensor(8);
		this.mDeviceAdmin = new ComponentName(this, AdminReceiver.class);
		
		this.mSensorManager.registerListener(this, this.mProximity, 3);
			
	}
	
	private boolean isActiveAdmin(){
	    return this.deviceManager.isAdminActive(this.mDeviceAdmin);
	}
	
	public void onDestroy(){
		if(mSensorManager != null)
			this.mSensorManager.unregisterListener(this);
	    super.onDestroy();
	}
	
	public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2){
		
		if (!isActiveAdmin())
	      {
	        Intent localIntent = new Intent(this, MainActivity.class);
	        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(localIntent);
	      }
		
		return paramInt1;
	}
	
	
}
