package com.easy.apps.easyopenvpn;

public class Util {

	private final String SPILT_STR = ",";
	private final int LAST_INDEX = -1;
	
	private String getTermFromIndex(String infoStr, int index) throws Exception{
		
		String retStr = "";
		
		String[] ary = infoStr.split(SPILT_STR);
		
		if(ary != null){
			
			if(index == LAST_INDEX)
				index = ary.length - 1;
			
			retStr = ary[index];
		}else
			throw new Exception();
		
		return retStr;
	}
}
