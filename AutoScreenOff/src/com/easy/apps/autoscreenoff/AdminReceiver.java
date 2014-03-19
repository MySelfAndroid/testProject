package com.easy.apps.autoscreenoff;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AdminReceiver extends DeviceAdminReceiver{
	
	public CharSequence onDisableRequested(Context paramContext, Intent paramIntent){
		return "Return the warning message to display to the user before being disabled";
	}
	
	public void onDisabled(Context paramContext, Intent paramIntent){
	    Log.d("AdminReceiver", "onDisabled");
	}

	public void onEnabled(Context paramContext, Intent paramIntent){
	    Log.d("AdminReceiver", "onEnabled");
	}
}
