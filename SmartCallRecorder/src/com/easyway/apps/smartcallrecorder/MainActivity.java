package com.easyway.apps.smartcallrecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dina.ui.model.BasicItem;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	static UITableView tableView;
	ProgressDialog progDialog;
	private SQL4 dbHelper;
	String filepath = Environment.getExternalStorageDirectory().getPath();
	static SharedPreferences prefs;
	
	ImageView iv;
	TextView tv;
	RelativeLayout rl;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getPreferences(MODE_PRIVATE);
        dbHelper = new SQL4(this);
     
        LayoutInflater inflater2 = LayoutInflater.from(this); // 1
        View itemView = inflater2.inflate(R.layout.item, null); // 2 and 3
        
        rl = (RelativeLayout)itemView.findViewById(R.id.rl);
        iv = (ImageView)itemView.findViewById(R.id.imageViewIcon);
        tv = (TextView)itemView.findViewById(R.id.textViewTitle);
        
        rl.setOnClickListener(new View.OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("@@@", "123123");
				boolean isEnable = getFlag();
		        if(isEnable){
		        	SharedPreferences.Editor editor = prefs.edit();
		        	editor.putBoolean("enbale", false).commit();
		        	iv.setImageDrawable(MainActivity.this.getResources().getDrawable(R.drawable.no));
		        	tv.setText(MainActivity.this.getString(R.string.enable_title_disable));
		        	
		        
		        }else{
		        	SharedPreferences.Editor editor = prefs.edit();
		        	editor.putBoolean("enbale", true).commit();
		        	
		        	iv.setImageDrawable(MainActivity.this.getResources().getDrawable(R.drawable.okk));
		        	tv.setText(MainActivity.this.getString(R.string.enable_title_enable));
		        }
				
			}
        	
        });
        
        boolean isEnable = getFlag();
        if(isEnable){
        	iv.setImageDrawable(this.getResources().getDrawable(R.drawable.okk));
        	tv.setText(this.getString(R.string.enable_title_enable));
        }else{
        	iv.setImageDrawable(this.getResources().getDrawable(R.drawable.no));
        	tv.setText(this.getString(R.string.enable_title_disable));
        }
        
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    }
    
    public static boolean getFlag(){
    	return prefs.getBoolean("enbale", true);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	reloadView();
    	
    	Intent intent = this.getIntent();
    	if(intent != null){
    		String fileName = intent.getStringExtra("action");
    		if(fileName!=null){
    			Log.d("@@@", fileName);
    			
    			final String recorderFile = filepath + File.separator + Recorder.AUDIO_RECORDER_FOLDER + File.separator +fileName + ".mp4";
    			
    			final File file = new File(recorderFile);
    			
    			if(file.exists()){
    				
    				new AlertDialog.Builder(MainActivity.this)
    	            .setIcon(R.drawable.ic_call)
    	            .setTitle(MainActivity.this.getString(R.string.title))
    	            .setMessage(MainActivity.this.getString(R.string.delete))
    	            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
    	                @Override
    	                public void onClick(DialogInterface dialog, int which) {
    	                	file.delete();
    	                	// TODO: delete db
    	                	dialog.dismiss();
    	                	reloadView();
    	                }
    	            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){

    					@Override
    					public void onClick(DialogInterface arg0, int arg1) {
    						// TODO Auto-generated method stub
    						arg0.dismiss();
    					}
    	            	
    	            })
    	            .show();
    			}else{
    				Log.d("@@@", "file:" +recorderFile+ "is not exist");
    			}
    			
    		}
    		else
    			Log.d("@@@", "action = null");
    	}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void reloadView(){
    	
    	new AsyncTask(){
    		@Override
            protected void onPreExecute() {
    			
    			progDialog = ProgressDialog.show(MainActivity.this, "",
    					MainActivity.this.getString(R.string.loading), true, true);
    			
    			if(tableView!=null)
    				tableView.clear();
    		}
    		
    		@Override
			protected Object doInBackground(Object... arg0) {
    			
    			List recorders = getRecordersList();
    			
    			if(recorders != null && recorders.size() >0){
    				
    				CustomClickListener listener = new CustomClickListener();
    				tableView.setClickListener(listener);
    				
    				for(int i=0;i<recorders.size();i++){
    					HashMap map = (HashMap)recorders.get(i);
    					String name = (String)map.get("name");
    					
    					if(name != null && !name.equals("")){
    						tableView.addBasicItem((String)map.get("filename"),name + " " + (String)map.get("number"));
    					}else
    						tableView.addBasicItem((String)map.get("filename"),(String)map.get("number"));
    					
    				}
    			
    			}
    			
    			return null;
    		}
    		
    		@Override
			protected void onPostExecute(Object result) {
    			
    			if(progDialog != null && progDialog.isShowing())
    				progDialog.dismiss();
    			
    			if(tableView!=null && tableView.getCount() > 0){
    				tableView.commit();
    			}else{
    				new AlertDialog.Builder(MainActivity.this)
				 	
		            .setIcon(R.drawable.ic_call)
		            .setTitle(MainActivity.this.getString(R.string.title))
		            .setMessage(MainActivity.this.getString(R.string.nodata))
		            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialog, int which) {
		                    //Stop the activity
		                	dialog.dismiss();
		                }
		            })
		            .show();
    			}
    		}
    	}.execute();
    }
    
    private List getRecordersList(){
    	
    	List alist = new ArrayList();
		Cursor c = dbHelper.getAll();
		if(c==null)
			return null;
		int rows_num = c.getCount();
		c.moveToFirst();
		for(int i=0; i<rows_num; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			String filename = c.getString(1);
			String number = c.getString(2);
			String name = c.getString(3);
			map.put("filename", filename);
			map.put("number", number);
			map.put("name", name);
			
			Log.d("@@@", filename +"," +number+","+name);
			
			alist.add(map);
			c.moveToNext();
		}
		c.close();
		return alist;
	}

    @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	  	 super.onCreateOptionsMenu(menu);
	  	 
	  	
	    MenuItem item4=menu.add(1,5,0,this.getString(R.string.menu1));
	       item4.setIcon(android.R.drawable.ic_menu_rotate);
	  	 
	  	 
	       return true;
	  }
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	  	switch (item.getItemId()) {
	  		
	  		case 5:
	  			
	  			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.easy.apps.easyopenvpn")));
	  			
	  			break;
	  			
	  	}
	  	
	  	return true;
	  }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            tableView = (UITableView) rootView.findViewById(R.id.tableView);
            
            
            return rootView;
        }
    }
    
	@Override
    protected void onDestroy(){
		if (dbHelper != null)
    		dbHelper.close();
		super.onDestroy();
	}
	
	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(final int index) {
			Context context = MainActivity.this;
			String [] items = {context.getString(R.string.item1),context.getString(R.string.item2),context.getString(R.string.item3)};
			
			BasicItem item = tableView.getItem(index);
			final String recorderFile = filepath + File.separator + Recorder.AUDIO_RECORDER_FOLDER + File.separator +item.getTitle() + ".mp4";
			
			new AlertDialog.Builder(MainActivity.this)
  	        .setIcon(R.drawable.ic_call)
  	        .setTitle(MainActivity.this.getString(R.string.title))
  	        .setCancelable(true)
  	        .setItems(items, new OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int count) {
					
					switch(count){
						case 0:
							
							File file = new File(recorderFile);
							if(file.exists()){
								
								//Toast.makeText(MainActivity.this,"hi", Toast.LENGTH_LONG).show();
								 Intent aIntent = new Intent();
								 aIntent.setAction(Intent.ACTION_VIEW);
								 aIntent.setDataAndType(Uri.fromFile(file), "video/mp4");
								 startActivity(aIntent);
							}else{
								 new AlertDialog.Builder(MainActivity.this)
								 	
						            .setIcon(R.drawable.ic_call)
						            .setTitle(MainActivity.this.getString(R.string.title))
						            .setMessage(MainActivity.this.getString(R.string.notfound))
						            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						                @Override
						                public void onClick(DialogInterface dialog, int which) {
						                    //Stop the activity
						                	dialog.dismiss();
						                }
						            })
						            .show();
							 
							}
						
							break;
						case 1:
							File file2 = new File(recorderFile);
							if(file2.exists()){
								file2.delete();
								
								new AlertDialog.Builder(MainActivity.this)
					            .setIcon(R.drawable.ic_call)
					            .setTitle(MainActivity.this.getString(R.string.title))
					            .setMessage(MainActivity.this.getString(R.string.delSuc))
					            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					                @Override
					                public void onClick(DialogInterface dialog, int which) {
					                    //Stop the activity
					                	dialog.dismiss();
					                	reloadView();
					                }
					            })
					            .show();
							}else{
								 new AlertDialog.Builder(MainActivity.this)
								 	
						            .setIcon(R.drawable.ic_call)
						            .setTitle(MainActivity.this.getString(R.string.title))
						            .setMessage(MainActivity.this.getString(R.string.notfound))
						            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						                @Override
						                public void onClick(DialogInterface dialog, int which) {
						                    //Stop the activity
						                	dialog.dismiss();
						                }
						            })
						            .show();
							 }
							break;
						case 2:
							
							Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
							Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
							    + File.separator + Recorder.AUDIO_RECORDER_FOLDER + File.separator);
							intent.setDataAndType(uri, "text/csv");
							startActivity(Intent.createChooser(intent, "Open folder"));
							
							break;
					}
					
					arg0.dismiss();
				}
  	        	
  	        }).show();
			
		}
		
	}
}
