package com.checkin.fb.checkinanywhere;


import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.checkin.fb.checkinanywhere.SessionEvents.AuthListener;
import com.checkin.fb.checkinanywhere.SessionEvents.LogoutListener;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements OnMapLongClickListener{
	private GoogleMap mMap;
	public static final String APP_ID = "525533487479040";
	final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	final static int PICK_EXISTING_PHOTO_RESULT_CODE = 1;
	String[] permissions = { "offline_access", "publish_stream", "user_photos", "publish_checkins",
	            "photo_upload" };
	private AdView adView;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        adView = (AdView)findViewById(R.id.adView2);
        adView.loadAd(new AdRequest());
        
        Utility.mFacebook = new Facebook(APP_ID);
        Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);
        
        SessionStore.restore(Utility.mFacebook, this);
        SessionEvents.addAuthListener(new FbAPIsAuthListener());
        SessionEvents.addLogoutListener(new FbAPIsLogoutListener());
        
        init(this, AUTHORIZE_ACTIVITY_RESULT_CODE, Utility.mFacebook, permissions);
        
        if (Utility.mFacebook.isSessionValid()) {
        	Log.d("FBCheckInActivity", "login");
        	
        }else
        	Log.d("FBCheckInActivity", "no login");
        
    }
    @Override
    protected void onDestroy(){
    	Log.e("123",  "onDestroy!" );
    	adView.destroy();
    	super.onDestroy();
    }
 
    @Override
    public void onResume() {
    	  super.onResume();
          if(Utility.mFacebook != null) {
              if (!Utility.mFacebook.isSessionValid()) {
            	  Toast.makeText(MainActivity.this, "Please login FB first", Toast.LENGTH_LONG).show();
              }else{
            	  Utility.mFacebook.extendAccessTokenIfNeeded(this, null);
            	  if(!checkIsTWLoacle())
            		  Toast.makeText(MainActivity.this, "Please use 'long-click' on map to check-in ^^", Toast.LENGTH_LONG).show();
            	  else
            		  Toast.makeText(MainActivity.this, "請在地圖上使用'長押按鈕'來進行打卡 ^^", Toast.LENGTH_LONG).show();
              }
          }
          
          mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
          
          if(mMap == null){
        	  
        	 new AlertDialog.Builder(this)
  	        .setIcon(android.R.drawable.ic_menu_info_details)
  	        .setTitle("Sorry")
  	        .setCancelable(false)
  	        .setMessage("Your google map service is too old")
  	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					
					Intent intent = new Intent();
	            	intent.putExtra("fail", "fail");
	            	
	            	intent.setClass(MainActivity.this, CheckInActivity.class);
	            	MainActivity.this.startActivity(intent);
				}
  	        
  	        });
          }else{
          
          mMap.setOnMapLongClickListener(this);
          mMap.setMyLocationEnabled(true);
          }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		Log.d("@@@", point.latitude +","+point.longitude);
		
		final double la = point.latitude;
		final double lo = point.longitude;
		
		mMap.setOnMapLongClickListener(null);
		
		if(checkIsTWLoacle()){
			new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_menu_info_details)
	        .setTitle("尋找地點打卡")
	        .setCancelable(false)
	        .setMessage("您想要搜尋附近的打卡地點嗎?")
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                //Stop the activity
	            	
	            	Intent intent = new Intent();
	            	intent.putExtra("la", la);
	            	intent.putExtra("lo", lo);
	            	intent.setClass(MainActivity.this, CheckInActivity.class);
	            	MainActivity.this.startActivity(intent);
	            	
	            	dialog.dismiss();
	            	mMap.setOnMapLongClickListener(MainActivity.this);
	            }
	        }).setNeutralButton("Cancel",  new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mMap.setOnMapLongClickListener(MainActivity.this);
					dialog.dismiss();
				}
	        	
	        })
	        .show();
		}else{
			new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_menu_info_details)
	        .setTitle("Check-in")
	        .setCancelable(false)
	        .setMessage("Would you like to check-in at near here?")
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                //Stop the activity
	            	
	            	Intent intent = new Intent();
	            	intent.putExtra("la", la);
	            	intent.putExtra("lo", lo);
	            	intent.setClass(MainActivity.this, CheckInActivity.class);
	            	MainActivity.this.startActivity(intent);
	            	
	            	dialog.dismiss();
	            	mMap.setOnMapLongClickListener(MainActivity.this);
	            }
	        }).setNeutralButton("Cancel",  new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mMap.setOnMapLongClickListener(MainActivity.this);
					dialog.dismiss();
				}
	        	
	        })
	        .show();
		}
		
		
		
		
	}
	
	 public void init(final Activity activity, final int activityCode, final Facebook fb,
	            final String[] permissions) {
	        /*mActivity = activity;
	        mActivityCode = activityCode;
	        mFb = fb;
	        mPermissions = permissions;
	        mHandler = new Handler();

	        setBackgroundColor(Color.TRANSPARENT);
	        setImageResource(fb.isSessionValid() ? R.drawable.logout_button : R.drawable.login_button);
	        drawableStateChanged();*/

	        SessionEvents.addAuthListener(new SessionListener());
	        SessionEvents.addLogoutListener(new SessionListener());
	        //setOnClickListener(new ButtonOnClickListener());
	        login();
	    }
	    
	    private void login(){
	    	  if (Utility.mFacebook.isSessionValid()) {
	              /*SessionEvents.onLogoutBegin();
	              AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(Utility.mFacebook);
	              asyncRunner.logout(FBCheckInActivity.this, new LogoutRequestListener());*/
	          } else {
	        	  Utility.mFacebook.authorize(MainActivity.this, permissions, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
	          }
	    }
	 
	    private final class LoginDialogListener implements DialogListener {
	        @Override
	        public void onComplete(Bundle values) {
	            SessionEvents.onLoginSuccess();
	            
	            if(checkIsTWLoacle()){
	            	  new AlertDialog.Builder(MainActivity.this)
	  	            .setIcon(android.R.drawable.ic_menu_info_details)
	  	            .setTitle("使用說明")		
	  	            .setMessage("您可以在世界地圖上使用'長壓'按鈕 來進行打卡，或是使用右上角的自動定位來找到您目前的位置 ^^")
	  	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	  	                @Override
	  	                public void onClick(DialogInterface dialog, int which) {
	  	                    //Stop the activity
	  	                
	  	                	dialog.dismiss();
	  	                	
	  	                }
	  	            }).show();
	            }else{
	            
	            new AlertDialog.Builder(MainActivity.this)
	            .setIcon(android.R.drawable.ic_menu_info_details)
	            .setTitle("Description")		
	            .setMessage("You can use 'long click' on any corner of the world map \r\n to check-In by Facebook, \r\n or you can select auto foucs button to find your location ^^")
	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    //Stop the activity
	                
	                	dialog.dismiss();
	                	
	                }
	            }).show();
	            }
	        }

	        @Override
	        public void onFacebookError(FacebookError error) {
	            SessionEvents.onLoginError(error.getMessage());
	        }

	        @Override
	        public void onError(DialogError error) {
	            SessionEvents.onLoginError(error.getMessage());
	        }

	        @Override
	        public void onCancel() {
	            SessionEvents.onLoginError("Action Canceled");
	            MainActivity.this.finish();
	        }
	    }
	    
	    private class SessionListener implements AuthListener, LogoutListener {

	        @Override
	        public void onAuthSucceed() {
	            //setImageResource(R.drawable.logout_button);
	            SessionStore.save( Utility.mFacebook, MainActivity.this);
	        }

	        @Override
	        public void onAuthFail(String error) {
	        }

	        @Override
	        public void onLogoutBegin() {
	        }

	        @Override
	        public void onLogoutFinish() {
	            SessionStore.clear(MainActivity.this);
	            //setImageResource(R.drawable.login_button);
	        }
	    }   
	 
	 private boolean checkIsTWLoacle(){
			
			try{
				
				Resources res = getResources();  
				Configuration config = res.getConfiguration();
				Locale mylocale = config.locale;
				String tmp = mylocale.getDisplayLanguage();
				
				if(tmp !=null && mylocale.getDisplayCountry() != null){
					Log.d("@@@", "locale:"+ tmp + ","+ mylocale.getDisplayCountry());
					
					if(tmp.contains("中文"))
						return true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return false;
		}
	    
	 public void requestUserData() {
	        //mText.setText("Fetching user name, profile pic...");
	        Bundle params = new Bundle();
	        params.putString("fields", "name, picture");
	        Utility.mAsyncRunner.request("me", params, new UserRequestListener());
	    }
	    
	 public class UserRequestListener extends BaseRequestListener {

	        @Override
	        public void onComplete(final String response, final Object state) {
	            JSONObject jsonObject;
	            try {
	                jsonObject = new JSONObject(response);

	                final String picURL = jsonObject.getString("picture");
	                final String name = jsonObject.getString("name");
	                Utility.userUID = jsonObject.getString("id");

	                /*mHandler.post(new Runnable() {
	                    @Override
	                    public void run() {
	                        mText.setText("Welcome " + name + "!");
	                        mUserPic.setImageBitmap(Utility.getBitmap(picURL));
	                    }
	                });*/

	            } catch (JSONException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	    }
	 
    public class FbAPIsAuthListener implements AuthListener {

        @Override
        public void onAuthSucceed() {
            requestUserData();
        }

        @Override
        public void onAuthFail(String error) {
            Toast.makeText(MainActivity.this, "Login Failed: " + error, Toast.LENGTH_LONG).show();
        }
    }
    
    public class FbAPIsLogoutListener implements LogoutListener {
        @Override
        public void onLogoutBegin() {
           
            Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLogoutFinish() {
            //mText.setText("You have logged out! ");
            Toast.makeText(MainActivity.this, "You have logged out!", Toast.LENGTH_LONG).show();
            //mUserPic.setImageBitmap(null);
        }
    }
}
