package com.easy.apps.easyopenvpn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServerFetcher {
	
	private final static String OPEN_VPN_CATAGORY_URL = "http://14.47.2.38:47511/api/iphone/";
	
	public static List<String> fetchFreeVpnServer(){
		InputStreamReader in = null;
		BufferedReader br = null;
		List<String> infos = new ArrayList<String>();
		try{
			URL url = new URL(OPEN_VPN_CATAGORY_URL);
			InputStream is = url.openStream();
			in = new InputStreamReader(is);
			StringBuilder sb=new StringBuilder();
			br = new BufferedReader(in);
			String read = br.readLine();
			
			while(read != null) {
			    sb.append(read);
			    read = br.readLine();
			    infos.add(read);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return infos;
	}
}
