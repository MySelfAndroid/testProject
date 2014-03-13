package com.easy.apps.easyopenvpn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final String TAG = this.getClass().getSimpleName();
	private ProgressDialog progressDialog;
	List<Map> dataList;
	ListView actualListView; 
	LockItemAdapter listItemAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actualListView = (ListView) findViewById(R.id.list);
		 
		actualListView.setClickable(true);
		actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
        
        });
		
		dataList = new ArrayList<Map>();
		
		refreshInfos();
		
		listItemAdapter = new LockItemAdapter(this, dataList);  
		actualListView.setAdapter(listItemAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private void refreshInfos() {
		// for testing
		new AsyncTask() {

			@Override
			protected void onPreExecute() {
				if(progressDialog!=null)
	    			progressDialog.dismiss();
				
				progressDialog = new ProgressDialog(MainActivity.this);
			    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			    progressDialog= ProgressDialog.show(MainActivity.this,"", "Loading vpn server list...",true,true);
			    
			    if(dataList !=null)
			    	dataList.clear();
			}

			@Override
			protected Object doInBackground(Object... arg0) {
				List<String> infos = ServerFetcher.fetchFreeVpnServer();
				
				Util util = new Util();
				List<Map<String,String>> finalInfos = util.parseServerInfo(infos);
				
				for (int i = 0; i < finalInfos.size(); i++) {
					Map<String, String> subInfos = finalInfos.get(i);
					Log.d(TAG, "IP:" + subInfos.get(Util.IP) + ", COUNTRY:"
							+ subInfos.get(Util.COUNTRY) + ", COUNTRY SHORT , " 
							+ subInfos.get(Util.COUNTRY_SHORT));
					
					String countryName = subInfos.get(Util.COUNTRY);
					if(countryName.indexOf("Korea") != -1){
						countryName = "Korea";
					}
					
					Map uiMap = new HashMap();
					uiMap.put(LockItemAdapter.APPNAME, subInfos.get(Util.COUNTRY));
					uiMap.put(LockItemAdapter.ICON, MainActivity.this.getCounrtyIcon(subInfos.get(Util.COUNTRY_SHORT)));
					dataList.add(uiMap);
				}
				
				listItemAdapter = new LockItemAdapter(MainActivity.this, dataList);  
				
				return listItemAdapter;
			}

			@Override
			protected void onPostExecute(Object result) {
				
				actualListView.setAdapter((LockItemAdapter)result);
				
				if(progressDialog!=null)
	    			progressDialog.dismiss();
			}
		}.execute();
	}
	
	private Drawable getCounrtyIcon(String name){
		try{
			int drawableResourceId = this.getResources().getIdentifier(name.toLowerCase(), "drawable", this.getPackageName());
			return this.getResources().getDrawable(drawableResourceId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void saveConfigFile(String ip,String country){
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + File.separator + TAG);
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		String fileName = country + "-" + ip + ".config";
		File file = new File(dir, fileName);
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(body.getBytes());
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
