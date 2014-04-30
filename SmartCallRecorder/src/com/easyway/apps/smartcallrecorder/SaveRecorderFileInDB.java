package com.easyway.apps.smartcallrecorder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

public class SaveRecorderFileInDB {
	
	static Context mContext;
	private static SQL4 dbHelper;
	
	public static Map queryLatestID(Context context){
		mContext = context;
		
		Log.d("@@@", "queryLatestID:");
		try{
			
		Map map = new HashMap();
		ContentResolver mContentResolver = mContext.getContentResolver();
		
		String[] projection = new String[] {CallLog.Calls._ID, CallLog.Calls.CACHED_NAME, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls.TYPE };
		
		Cursor calls = null;
    	calls = mContentResolver.query(CallLog.Calls.CONTENT_URI, projection,
                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    	
    	 calls.moveToFirst();
    	
    	 String date = calls.getString(calls
                .getColumnIndex(CallLog.Calls.DATE));
    	 
    	 String name= calls.getString(calls
                 .getColumnIndex(CallLog.Calls.CACHED_NAME));
    	 
    	 SimpleDateFormat sdf = new SimpleDateFormat();
    	 String time = sdf.format(Long.parseLong(date));
    	 
    	 Log.d("@@@", "latest date:"+ date);
    	 Log.d("@@@", "latest time:"+ time);
    	 Log.d("@@@", "latest name:"+ name);
    	 calls.close();
    	 
    	 map.put("date", date);
    	 map.put("name", name);
    	 
    	 return map;
    	 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map queryLatestNumber(Context context){
		Log.d("@@@", "queryLatestNumber:");
		try{
			mContext = context;
			ContentResolver mContentResolver = mContext.getContentResolver();
			
			String[] projection = new String[] {CallLog.Calls._ID, CallLog.Calls.CACHED_NAME, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls.TYPE };
			
			Cursor calls = null;
	    	calls = mContentResolver.query(CallLog.Calls.CONTENT_URI, projection,
	                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
	    	
	    	 calls.moveToFirst();
	    	
	    	 String number = calls.getString(calls
	                .getColumnIndex(CallLog.Calls.NUMBER));
	    	 
	    	 String date = calls.getString(calls
		                .getColumnIndex(CallLog.Calls.DATE));
	    	 
	    	 String dur = calls.getString(calls
		                .getColumnIndex(CallLog.Calls.DURATION));
	    	 
	    	 String name = calls.getString(calls
		                .getColumnIndex(CallLog.Calls.CACHED_NAME
		                		));
	    	 
	    	 Map map = new HashMap();
	    	 map.put("number", number);
	    	 map.put("date", date);
	    	 map.put("dur", dur);
	    	 map.put("name", name);
	    	 
	    	 calls.close();
	    	 
	    	 return map;
	    	 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void saveFileNameInDB(Context context, String fileName, String date, String name){
		
		Log.d("@@@", "saveFileNameInDB:");
		
		if(mContext !=null)
			mContext = context;
		
		dbHelper = new SQL4(mContext);
		
		try {
			Log.d("@@@", "create filename in db: "+ dbHelper.create(fileName, date, name));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbHelper.close();
		}
	}
	
	public class Bean{
		
		private String number;
		private String date;
	}
}
