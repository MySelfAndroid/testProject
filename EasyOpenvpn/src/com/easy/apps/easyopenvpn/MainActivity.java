package com.easy.apps.easyopenvpn;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final String TAG = this.getClass().getSimpleName();
	private ProgressDialog progressDialog;
	private final static int OPENVPN = 1;
	List<Map> dataList;
	ListView actualListView; 
	LockItemAdapter listItemAdapter;
	private String mFullFilePath;
	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adView = (AdView)findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());
		
		actualListView = (ListView) findViewById(R.id.list);
		 
		actualListView.setClickable(true);
		actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Map uiSelectMap = dataList.get(arg2);
				String ip = (String)uiSelectMap.get(Util.IP);
				String base64Data = (String)uiSelectMap.get(Util.CONFIGDATA);
				String name = (String)uiSelectMap.get(LockItemAdapter.APPNAME);
				
				String fullFilePath = MainActivity.this.saveConfigFile(ip, name, base64Data);
				Log.d(TAG, "file path:" + fullFilePath);
				mFullFilePath = fullFilePath;
				openOpenvpnIntent(fullFilePath);
			}
        
        });
		
		dataList = new ArrayList<Map>();
		
		refreshInfos();
		
		listItemAdapter = new LockItemAdapter(this, dataList);  
		actualListView.setAdapter(listItemAdapter);
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
			    progressDialog= ProgressDialog.show(MainActivity.this,MainActivity.this.getString(R.string.loading), MainActivity.this.getString(R.string.loading_msg),true,true);
			    
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
					uiMap.put(LockItemAdapter.APPNAME, countryName);
					uiMap.put(LockItemAdapter.ICON, MainActivity.this.getCounrtyIcon(subInfos.get(Util.COUNTRY_SHORT)));
					uiMap.put(Util.IP, subInfos.get(Util.IP));
					uiMap.put(Util.CONFIGDATA, subInfos.get(Util.CONFIGDATA));
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
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	  	 super.onCreateOptionsMenu(menu);
	  	 
	  	
	    MenuItem item4=menu.add(1,5,0,this.getString(R.string.resync));
	       item4.setIcon(android.R.drawable.ic_menu_rotate);
	  	 
	  	 
	       return true;
	  }
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	  	switch (item.getItemId()) {
	  		
	  		case 5:
	  			
	  			refreshInfos();
	  			
	  			break;
	  			
	  	}
	  	
	  	return true;
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
	
	private String saveConfigFile(String ip,String country,String base64Data){
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + File.separator + TAG);
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		String fileName = country + "-" + ip + ".config";
		File file = new File(dir, fileName);
		
		String fullFilePath = sdCard.getAbsolutePath() + File.separator + TAG + File.separator + fileName;
		
		try {
			String configData = Base64Coder.decodeString(base64Data);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(configData.getBytes());
	        fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fullFilePath;
	}
	
	@Override
    protected void onDestroy(){
		if(adView!=null)
			adView.destroy();
		
    	super.onDestroy();
	}
	
	private void openOpenvpnIntent(String fullFillName){
		try{
			Intent localIntent = new Intent("android.intent.action.VIEW");
		    localIntent.addCategory("android.intent.category.DEFAULT");
		    localIntent.setDataAndType(Uri.fromFile(new File(fullFillName)), "application/x-openvpn-profile");
		    
		    if (!isInstalledOpenVPN()){
		    	new AlertDialog.Builder(this)
		    	.setTitle(android.R.string.dialog_alert_title)
		    	.setIcon(android.R.drawable.ic_dialog_info)
		    	.setMessage(this.getString(R.string.download))
		    	.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intentPro = new Intent("android.intent.action.VIEW");
			  			intentPro.setData(Uri.parse("market://details?id=net.openvpn.openvpn"));
			            startActivity(intentPro);
			            dialog.dismiss();
					}
				}).show();
		    }else{
		    	this.startActivityForResult(localIntent, OPENVPN);
		    }
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == OPENVPN){
			
			 if (resultCode != -1){
				 
				 if(mFullFilePath != null){
					 
					 File tmpFile = new File(mFullFilePath);
					 
					 if(tmpFile.exists())
						 tmpFile.delete();
				 }
			 }
		}
	}
	
	private boolean isInstalledOpenVPN(){
		boolean result = false;
		final PackageManager pm = getPackageManager();
		//get a list of installed apps.
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo packageInfo : packages) {
			if(packageInfo.packageName.equals("net.openvpn.openvpn")){
				result = true;
				break;
			}else if(packageInfo.packageName.equals("de.schaeuffelhut.android.openvpn")){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
}
