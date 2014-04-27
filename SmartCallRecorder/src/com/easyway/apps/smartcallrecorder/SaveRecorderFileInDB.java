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
	
	public static String queryLatestID(Context context){
		mContext = context;
		
		Log.d("@@@", "queryLatestID:");
		try{
			
		
		ContentResolver mContentResolver = mContext.getContentResolver();
		
		String[] projection = new String[] {CallLog.Calls._ID, CallLog.Calls.CACHED_NAME, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls.TYPE };
		
		Cursor calls = null;
    	calls = mContentResolver.query(CallLog.Calls.CONTENT_URI, projection,
                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    	
    	 calls.moveToFirst();
    	
    	 String date = calls.getString(calls
                .getColumnIndex(CallLog.Calls.DATE));
    	 
    	 SimpleDateFormat sdf = new SimpleDateFormat();
    	 String time = sdf.format(Long.parseLong(date));
    	 
    	 Log.d("@@@", "latest date:"+ date);
    	 Log.d("@@@", "latest time:"+ time);
    	 calls.close();
    	 
    	 return date;
    	 
		}catch(Exception e){
			e.printStackTrace();
			return "no date";
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
	    	 
	    	 
	    	 
	    	 Map map = new HashMap();
	    	 map.put("number", number);
	    	 map.put("date", date);
	    	 map.put("dur", dur);
	    	 
	    	 calls.close();
	    	 
	    	 return map;
	    	 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void saveFileNameInDB(Context context, String fileName, String date){
		
		Log.d("@@@", "saveFileNameInDB:");
		
		if(mContext !=null)
			mContext = context;
		
		dbHelper = new SQL4(mContext);
		
		try {
			Log.d("@@@", "create filename in db: "+ dbHelper.create(fileName, date));
			
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
