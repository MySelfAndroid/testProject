package com.gogo.linetheme.themefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	List<Map> dataList;
	ListView actualListView; 
	LockItemAdapter listItemAdapter;
	private ProgressDialog progressDialog;
	private RelativeLayout rl1;
	private RelativeLayout rl2;
	private RelativeLayout rl3;
	private AdView adView;
	private final String old__native_themefile = "/mnt/sdcard/Android/data/jp.naver.line.android/theme/a0768339-c2d3-4189-9653-2909e9bb6f58/themefile";
	private final String new__native_themefile = "/mnt/sdcard/Android/data/jp.naver.line.android/theme/a0768339-c2d3-4189-9653-2909e9bb6f58/themefile.5";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adView = (AdView)findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());
		
		rl1 = (RelativeLayout)this.findViewById(R.id.lv1);
		rl2 = (RelativeLayout)this.findViewById(R.id.lv2);
		rl3 = (RelativeLayout)this.findViewById(R.id.lv3);
		
		
		rl1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainActivity.this.refreshInfos();
			}
			
		});
		
		
		rl2.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new AlertDialog.Builder(MainActivity.this).setTitle(MainActivity.this.getString(R.string.dlinfo)).setItems(new String[]{MainActivity.this.getString(R.string.dlitem1),MainActivity.this.getString(R.string.dlitem2)}, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String url = "";
						if(which == 0){
							url= "http://apk.tw/forum.php?mod=forumdisplay&fid=543&filter=typeid&typeid=4561&fromuid=870912";
						}else if(which == 1){
							url= "http://www.kikinote.com/article/5097.html";
						} 
						
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
						
						dialog.dismiss();
					}
					
				}).show();
			}
			
		});
		
rl3.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new AlertDialog.Builder(MainActivity.this).setTitle(MainActivity.this.getString(R.string.howtoUse)).setMessage(MainActivity.this.getString(R.string.info2)).setPositiveButton("OK",  new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();
					}
					
				}).show();
			}
			
		});
		
		
		actualListView = (ListView) findViewById(R.id.list);	 
		actualListView.setClickable(true);
		actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Map map = (Map) dataList.get(arg2);
				final File file = (File)map.get("File");
				
				new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(MainActivity.this.getString(R.string.alert_title)
						).setMessage(MainActivity.this.getString(R.string.confirm_msg)).setPositiveButton("OK", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0,
									int arg1) {
								// TODO Auto-generated method stub
								
								boolean b = MainActivity.this.copyTheme2NatviePath(file);
								
								if(!b){
									new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(MainActivity.this.getString(R.string.alert_title)
											).setMessage(MainActivity.this.getString(R.string.copyfail_msg)).setPositiveButton("OK", new DialogInterface.OnClickListener(){

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated method stub
													
													new AlertDialog.Builder(MainActivity.this).setTitle(MainActivity.this.getString(R.string.attend)).setMessage(MainActivity.this.getString(R.string.info)).setPositiveButton("OK", new DialogInterface.OnClickListener(){

														@Override
														public void onClick(DialogInterface dialog, int which) {
															dialog.dismiss();
														}
														}).show();
													
													dialog.dismiss();
												}
												
											}).show();

								}else{
									new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(MainActivity.this.getString(R.string.alert_title)
											).setMessage(MainActivity.this.getString(R.string.info)).setPositiveButton("OK", new DialogInterface.OnClickListener(){

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated method stub
													dialog.dismiss();
												}
												
											}).show();
								}
								
								arg0.dismiss();
								
								
								
							}
							
						}).setNegativeButton("No", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
							
						}).show();
				
			}
			
		});
		
		dataList = new ArrayList<Map>();
		
		listItemAdapter = new LockItemAdapter(this, dataList);  
		actualListView.setAdapter(listItemAdapter);
		
		refreshInfos();
		
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
			    progressDialog= ProgressDialog.show(MainActivity.this,MainActivity.this.getString(R.string.loading), MainActivity.this.getString(R.string.loading_msg),true,true);
			    
			    if(dataList !=null)
			    	dataList.clear();
			}

			@Override
			protected Object doInBackground(Object... arg0) {
				ThemeFetcher fetcher = new ThemeFetcher();
				
				List<File> infos = fetcher.getAllThemeFiles();
				
				for(File file: infos){
					Map uiMap = new HashMap();
					uiMap.put(LockItemAdapter.APPNAME, file.getName());
					uiMap.put(LockItemAdapter.ICON,MainActivity.this.getResources().getDrawable(R.drawable.icon_download));
					uiMap.put(LockItemAdapter.IP, file.getPath());
					uiMap.put("File", file);
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
				
				if(dataList.size() <=0){
					new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_info).setTitle(MainActivity.this.getString(R.string.alert_title)
							).setMessage(MainActivity.this.getString(R.string.alert_msg)).setPositiveButton("OK", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									arg0.dismiss();
								}
								
							}).show();
				}
				
			}
		}.execute();
	}
	private String getNativePath(){
		
		try{
			int version = this.getPackageManager().getPackageInfo("jp.naver.line.android", 0).versionCode;
			if(version > 150){
				return new__native_themefile;
			}else
				return old__native_themefile;
		}catch(Exception e){
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	private boolean copyTheme2NatviePath(File themeFile){
		try{
			Log.d("@@@", "1");
			FileChannel localFileChannel1 = new FileInputStream(themeFile).getChannel();
			File localFile = new File(this.getNativePath());
		      FileChannel localFileChannel2 = new FileOutputStream(this.getNativePath()).getChannel();
		      if (!localFile.exists())
		        localFile.createNewFile();
		      Log.d("@@@", "2");
		      localFileChannel2.transferFrom(localFileChannel1, 0L, localFileChannel1.size());
		      
		      if (localFileChannel1 != null)
		          localFileChannel1.close();
		        if (localFileChannel2 != null)
		          localFileChannel2.close();
		        Log.d("@@@", "3");
		        return true;
		      
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
    protected void onDestroy(){
		if(adView!=null)
			adView.destroy();
		
    	super.onDestroy();
	}

}
