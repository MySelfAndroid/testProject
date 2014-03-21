package com.apps.easy.rootchecker;

import java.io.File;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView tv1;
	private TextView tv2;
	private RelativeLayout rl1;	
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv1 = (TextView) this.findViewById(R.id.textView1);
		tv2 = (TextView) this.findViewById(R.id.textView2);
		rl1 = (RelativeLayout) this.findViewById(R.id.relativeLayout1);
		
		rl1.setOnClickListener(new OnClickListener(){

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void onClick(View arg0) {
				
				new AsyncTask(){
		    		
		    		@Override
		            protected void onPreExecute() {
		    		}

					@Override
					protected Object doInBackground(Object... params) {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(isRooted()){
							return true;
						}else{
							return false;
						}
					}
					
					@Override
					protected void onPostExecute(Object result) {
						Boolean b = (Boolean) result;
						if(b){
							rl1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
							tv2.setTextColor(Color.WHITE);
							tv2.setText(R.string.result_already);
						}else{
							rl1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.green_light));
							tv2.setTextColor(Color.WHITE);
							tv2.setText(R.string.result_not);
						}
						
					}
		    	
		    	}.execute();
				
				if(isRooted()){
					rl1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.red));
					tv2.setTextColor(Color.WHITE);
					tv2.setText(R.string.result_already);
				}else{
					rl1.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.green_light));
					tv2.setTextColor(Color.WHITE);
					tv2.setText(R.string.result_not);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private static boolean isRooted() {
	    return findBinary("su");
	}

	public static boolean findBinary(String binaryName) {
	    boolean found = false;
	    if (!found) {
	        String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
	                "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
	        for (String where : places) {
	            if ( new File( where + binaryName ).exists() ) {
	                found = true;
	                break;
	            }
	        }
	    }
	    return found;
	}

}
