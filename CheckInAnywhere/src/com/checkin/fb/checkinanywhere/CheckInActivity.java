package com.checkin.fb.checkinanywhere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CheckInActivity  extends Activity implements OnItemClickListener{
	private Handler mHandler;
	protected ListView placesList;
	protected static JSONArray jsonArray;
	protected ProgressDialog dialog;
	private JSONObject location;
	private boolean flag = false;
    final static double TIMES_SQUARE_LAT = 40.756;
    final static double TIMES_SQUARE_LON = -73.987;
    private AdView adView;
    protected LocationManager lm;
    protected MyLocationListener locationListener;
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub

        if (!Utility.mFacebook.isSessionValid()) {
            Util.showAlert(this, "Warning", "You must first log in.");
        } else {
            try {
                //final String message = "Check-in from the " + getString(R.string.app_name);
            	final String message = "";
                final String name = jsonArray.getJSONObject(position).getString("name");
                final String placeID = jsonArray.getJSONObject(position).getString("id");
                new AlertDialog.Builder(this).setTitle("Check-in?")
                        .setMessage(String.format("Would you like to check-in at %1$s?", name))
                        .setPositiveButton("Check-In", new DialogInterface.OnClickListener() {
                            /*
                             * Source tag: check_in_tag Check-in user at the
                             * selected location posting to the me/checkins
                             * endpoint. More info here:
                             * https://developers.facebook
                             * .com/docs/reference/api/user/ - checkins
                             */
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle params = new Bundle();
                                
                                /*if(flag){
                                	String tmp ="";
                                	if(FriendsList.tags != null && FriendsList.tags.length >0){
                                		for(int i=0;i<FriendsList.tags.length;i++){
                                			Log.d("CheckInActivity", FriendsList.tags[i]);
                                			tmp += FriendsList.tags[i];
                                			if( i!=FriendsList.tags.length -1)
                                				tmp += ", ";
                         
                                			
                                		}
                                		Log.d("CheckInActivity","tags:"+ tmp);
                                	}
                                	params.putStringArray("tags",  FriendsList.tags);
                                	params.putString("tags", tmp);
                                	FriendsList.ulist.clear();
                                }*/
                                params.putString("place", placeID);
                                params.putString("message", message);
                                params.putString("coordinates", location.toString());
                                Utility.mAsyncRunner.request("me/checkins", params, "POST",
                                        new placesCheckInListener(), null);
                            }
                        }).setNegativeButton("Cancel", null).show();
            } catch (JSONException e) {
                showToast("Error: " + e.getMessage());
            }
        }
    
	}
	
	
	public class placesCheckInListener extends BaseRequestListener {
        @Override
        public void onComplete(final String response, final Object state) {
            showToast("Facebook Check-In successful, Checking with Facebook APP ");
          
            mHandler.postDelayed(new Thread(){
            	@Override
            	public void run(){
            	   // Looper.prepare();
            		Intent intent = new Intent("android.intent.category.LAUNCHER");
            		intent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");
            		startActivity(intent);
            		finish();
            		//Looper.loop();
            	}
            }, 2500);
        }

        public void onFacebookError(FacebookError error) {
            dialog.dismiss();
            showToast("Check-in Error: " + error.getMessage());
        }
    }
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.places_list);
	        
	        adView = (AdView)findViewById(R.id.adView2);
	        adView.loadAd(new AdRequest());
	        
	        mHandler = new Handler();
	        location = new JSONObject();
	        
	        double la = this.getIntent().getDoubleExtra("la",TIMES_SQUARE_LAT);
	        double lo = this.getIntent().getDoubleExtra("lo",TIMES_SQUARE_LON);
	        
	        String fail = this.getIntent().getStringExtra("fail");
	        
	        if(fail != null)
	        {
	          this.getLocation();
	          return;
	        }
	        
	        try {
                location.put("latitude", la);
                location.put("longitude", lo);
            } catch (JSONException e) {
            }
            fetchPlaces();
	        
	 }
	 
	 public void getLocation() {
	        /*
	         * launch a new Thread to get new location
	         */
	        new Thread() {
	            @Override
	            public void run() {
	                Looper.prepare();
	                dialog = ProgressDialog.show(CheckInActivity.this, "",
	                        "search..", false, true,
	                        new DialogInterface.OnCancelListener() {
	                            @Override
	                            public void onCancel(DialogInterface dialog) {
	                                showToast("No location fetched.");
	                            }
	                        });

	                if (lm == null) {
	                    lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	                }

	                if (locationListener == null) {
	                    locationListener = new MyLocationListener();
	                }

	                Criteria criteria = new Criteria();
	                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	                String provider = lm.getBestProvider(criteria, true);
	                if (provider != null && lm.isProviderEnabled(provider)) {
	                    lm.requestLocationUpdates(provider, 1, 0, locationListener,
	                            Looper.getMainLooper());
	                } else {
	                    /*
	                     * GPS not enabled, prompt user to enable GPS in the
	                     * Location menu
	                     */
	                    new AlertDialog.Builder(CheckInActivity.this)
	                            .setTitle("")
	                            .setMessage("Enable GPS")
	                            .setPositiveButton("OK",
	                                    new DialogInterface.OnClickListener() {
	                                        @Override
	                                        public void onClick(DialogInterface dialog, int which) {
	                                            startActivityForResult(
	                                                    new Intent(
	                                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
	                                                    0);
	                                        }
	                                    })
	                            .setNegativeButton("Cancel",
	                                    new DialogInterface.OnClickListener() {
	                                        @Override
	                                        public void onClick(DialogInterface dialog, int which) {
	                                            dialog.dismiss();
	                                            CheckInActivity.this.finish();
	                                        }
	                                    }).show();
	                }
	                Looper.loop();
	            }
	        }.start();
	    }
	 
	 private void fetchPlaces() {
	        if (!isFinishing()) {
	            dialog = ProgressDialog.show(CheckInActivity.this, "", "Fetching nearby places...", true,
	                    true, new DialogInterface.OnCancelListener() {
	                        @Override
	                        public void onCancel(DialogInterface dialog) {
	                            showToast("No places fetched.");
	                        }
	                    });
	        }
	        /*
	         * Source tag: fetch_places_tag
	         */
	        Bundle params = new Bundle();
	        params.putString("type", "place");
	        try {
	            params.putString("center",
	                    location.getString("latitude") + "," + location.getString("longitude"));
	        } catch (JSONException e) {
	            showToast("No places fetched.");
	            return;
	        }
	        params.putString("distance", "35000");
	        Utility.mAsyncRunner.request("search", params, new placesRequestListener());
	    }
	 
	 public void showToast(final String msg) {
	        mHandler.post(new Runnable() {
	            @Override
	            public void run() {
	                Toast toast = Toast.makeText(CheckInActivity.this, msg, Toast.LENGTH_LONG);
	                toast.show();
	            }
	        });
	  }
	 
	 @Override
	    protected void onDestroy(){
	    	Log.e("123",  "onDestroy!" );
	    	adView.destroy();
	    	super.onDestroy();
	    }
	 
	 public class placesRequestListener extends BaseRequestListener {

	        @Override
	        public void onComplete(final String response, final Object state) {
	            Log.d("Facebook-FbAPIs", "Got response: " + response);
	            dialog.dismiss();

	            try {
	                jsonArray = new JSONObject(response).getJSONArray("data");
	                if (jsonArray == null) {
	                    showToast("Error: nearby places could not be fetched");
	                    return;
	                }
	            } catch (JSONException e) {
	                showToast("Error: " + e.getMessage());
	                return;
	            }
	            mHandler.post(new Runnable() {
	                @Override
	                public void run() {
	                    placesList = (ListView) findViewById(R.id.places_list);
	                    placesList.setOnItemClickListener(CheckInActivity.this);
	                    placesList.setAdapter(new PlacesListAdapter(CheckInActivity.this));
	                }
	            });

	        }

	        public void onFacebookError(FacebookError error) {
	            dialog.dismiss();
	            showToast("Fetch Places Error: " + error.getMessage());
	        }
	    }
	 
	 public class PlacesListAdapter extends BaseAdapter {
	        private LayoutInflater mInflater;
	        CheckInActivity placesList;

	        public PlacesListAdapter(Context context) {
	            mInflater = LayoutInflater.from(context);
	        }

	        @Override
	        public int getCount() {
	            return jsonArray.length();
	        }

	        @Override
	        public Object getItem(int position) {
	            return null;
	        }

	        @Override
	        public long getItemId(int position) {
	            return 0;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            JSONObject jsonObject = null;
	            try {
	                jsonObject = jsonArray.getJSONObject(position);
	            } catch (JSONException e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	            View hView = convertView;
	            if (convertView == null) {
	                hView = mInflater.inflate(R.layout.place_item, null);
	                ViewHolder holder = new ViewHolder();
	                holder.name = (TextView) hView.findViewById(R.id.place_name);
	                holder.location = (TextView) hView.findViewById(R.id.place_location);
	                hView.setTag(holder);
	            }

	            ViewHolder holder = (ViewHolder) hView.getTag();
	            try {
	                holder.name.setText(jsonObject.getString("name"));
	            } catch (JSONException e) {
	                holder.name.setText("");
	            }
	            try {
	                String location = jsonObject.getJSONObject("location").getString("street") + ", "
	                        + jsonObject.getJSONObject("location").getString("city") + ", "
	                        + jsonObject.getJSONObject("location").getString("state");
	                holder.location.setText(location);
	            } catch (JSONException e) {
	                holder.location.setText("");
	            }
	            return hView;
	        }

	    }

	    class ViewHolder {
	        TextView name;
	        TextView location;
	    }
	    
	    class MyLocationListener implements LocationListener {

	        @Override
	        public void onLocationChanged(Location loc) {
	            dialog.dismiss();
	            if (loc != null) {
	                try {
	                    location.put("latitude", new Double(loc.getLatitude()));
	                    location.put("longitude", new Double(loc.getLongitude()));
	                } catch (JSONException e) {
	                }
	                /*showToast("Location acquired: " + String.valueOf(loc.getLatitude()) + " "
	                        + String.valueOf(loc.getLongitude()));*/
	                lm.removeUpdates(this);
	                fetchPlaces();
	            }
	        }

	        @Override
	        public void onProviderDisabled(String provider) {
	        }

	        @Override
	        public void onProviderEnabled(String provider) {
	        }

	        @Override
	        public void onStatusChanged(String provider, int status, Bundle extras) {
	        }
	    }
}
