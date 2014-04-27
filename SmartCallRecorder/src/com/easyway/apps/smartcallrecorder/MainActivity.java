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
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	UITableView tableView;
	ProgressDialog progDialog;
	private SQL4 dbHelper;
	String filepath = Environment.getExternalStorageDirectory().getPath();
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tableView = (UITableView)this.findViewById(R.id.tableView);
        dbHelper = new SQL4(this);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	reloadView();
    }
    
    private void reloadView(){
    	
    	new AsyncTask(){
    		@Override
            protected void onPreExecute() {
    			
    			progDialog = ProgressDialog.show(MainActivity.this, "",
     	                "Loding...", true, true);
    			
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
    					tableView.addBasicItem((String)map.get("filename"),(String)map.get("number"));
    				}
    			
    			}
    			
    			return null;
    		}
    		
    		@Override
			protected void onPostExecute(Object result) {
    			
    			if(progDialog != null && progDialog.isShowing())
    				progDialog.dismiss();
    			
    			if(tableView.getCount() > 0){
    				tableView.commit();
    			}
    		}
    	};
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
			map.put("filename", filename);
			map.put("number", number);
			alist.add(map);
			c.moveToNext();
		}
		c.close();
		return alist;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
			String [] items = {"Play","Delete","Open"};
			
			BasicItem item = tableView.getItem(index);
			final String recorderFile = filepath + File.separator + Recorder.AUDIO_RECORDER_FOLDER + File.separator +item.getTitle() + ".mp4";
			
			new AlertDialog.Builder(MainActivity.this)
  	        .setIcon(android.R.drawable.ic_menu_more)
  	        .setTitle("Sorry")
  	        .setCancelable(false)
  	        .setItems(items, new OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int count) {
					
					switch(count){
						case 0:
							
							File file = new File(recorderFile);
							if(file.exists()){
								
								Toast.makeText(MainActivity.this,"hi", Toast.LENGTH_LONG).show();
								 Intent aIntent = new Intent();
								 aIntent.setAction(Intent.ACTION_VIEW);
								 aIntent.setDataAndType(Uri.fromFile(file), "video/mp4");
								 startActivity(aIntent);
							}else{
								 new AlertDialog.Builder(MainActivity.this)
								 	
						            .setIcon(android.R.drawable.ic_dialog_info)
						            .setTitle("Alert")
						            .setMessage("not found")
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
					            .setIcon(android.R.drawable.ic_dialog_info)
					            .setTitle("Alert")
					            .setMessage("del success")
					            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					                @Override
					                public void onClick(DialogInterface dialog, int which) {
					                    //Stop the activity
					                	dialog.dismiss();
					                }
					            })
					            .show();
							}else{
								 new AlertDialog.Builder(MainActivity.this)
								 	
						            .setIcon(android.R.drawable.ic_dialog_info)
						            .setTitle("Alert")
						            .setMessage("not found")
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
							break;
					}
					
					arg0.dismiss();
				}
  	        	
  	        }).show();
			
		}
		
	}
}
