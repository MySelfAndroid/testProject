package com.easy.apps.easyopenvpn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class Util {
	private final String TAG = this.getClass().getSimpleName();
	private final String SPILT_STR = ",";
	private final int LAST_INDEX = -1;
	private final int IP_INDEX = 1;
	private final int Country_INDEX = 5;
	public final static String IP = "IP";
	public final static String COUNTRY = "COUNTRY";
	public final static String CONFIGDATA = "CONFIGDATA";
	public final static String COUNTRY_SHORT = "COUNTRY_SHORT";
	public  final int  Country_Short_INDEX = 6;
	
	private String getTermFromIndex(String infoStr, int index) throws Exception{		
		String retStr = "";
		String[] ary = infoStr.split(SPILT_STR);
		
		if(ary != null){
			
			if(index == LAST_INDEX)
				index = ary.length - 1;
			
			retStr = ary[index];
		}else{
			Log.d(TAG, "server list ary is null");
			throw new Exception();
		}
		
		return retStr;
	}
	
	public List<Map<String,String>> parseServerInfo(List<String> sourceInfos){
		List<Map<String,String>> resultInfo = new ArrayList<Map<String,String>>();
		
		try{
			for(String str:sourceInfos){
				Map<String,String> map = new HashMap<String,String>();
				
				// get IP
				String ip = this.getTermFromIndex(str, IP_INDEX);
				if(ip.equals(IP))
					continue;
				map.put(IP, ip);
				
				// get Country
				String country = this.getTermFromIndex(str, Country_INDEX);
				map.put(COUNTRY, country);
				
				// get Country short
				String country_short = this.getTermFromIndex(str, Country_Short_INDEX);
				map.put(COUNTRY_SHORT, country_short);
				
				// get configData
				String configData = this.getTermFromIndex(str, LAST_INDEX);
				map.put(CONFIGDATA, configData);
				
				resultInfo.add(map);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resultInfo;
	}
}
