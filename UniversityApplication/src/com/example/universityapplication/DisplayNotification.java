package com.example.universityapplication;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DisplayNotification extends ActionBarActivity {
	

	// some variables for the dynamic ListView
	private ListView lv;
	private ArrayList<String> als;
	private ArrayList<String> localstorage;
	private ArrayAdapter<String> adapter;
	private static final String KEY = "myNotifications";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_notification);
		lv = (ListView) findViewById(R.id.lv_notifications);
		
		// Activate Up Button in Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // if there is a internet connection available
        // connect to the server
        // AsyncHttpResonseHandler opens another thread
     	if (getConnectivityStatus(getApplicationContext())){

		// connect to the server, opens another thread
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String server = setting_prefs.getString("server_address", null);
        String addr = new StringBuilder().append("http://").append(server).append("/notifications").toString();
    
		client.get(addr, params, new AsyncHttpResponseHandler(){
	
			// if connection fails
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Toast.makeText(getApplicationContext(), "Network error while connecting to server", Toast.LENGTH_LONG).show();	
			}
	
			// if connection is successful
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				try {
					// transfer data from backend server
					JSONObject obj = new JSONObject(new String(response));
					// notifications = array with all notifications
					JSONArray notifications = obj.getJSONArray("notifications");
					als = new ArrayList<String>();
					// extract the data of each individual notification and save it as a string
					// assembly them into the composition string, which will be added to als
					for (int i = 0; i < notifications.length(); i++) {
						  JSONObject notification = notifications.getJSONObject(i);
						  JSONObject fields = notification.getJSONObject("fields");
						  String text = fields.getString("text");
						  String date = fields.getString("date");
						  String user = fields.getString("user");
						  String composition = (text + " // from " + user + " // sent on " + date).toString();
						  als.add(composition);
						}
					
					// use adapter to display the list view
					adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, als);
					lv.setAdapter(adapter);
					
				} catch (JSONException e) {
					// displays exception
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
			}
			
		});
     	}
     	// if there is no internet connection available
     	else {
     				if (savedInstanceState != null && savedInstanceState.containsKey(KEY)){
     					// get data from local storage
     					localstorage = savedInstanceState.getStringArrayList(KEY);
     					adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, localstorage);
     					lv.setAdapter(adapter);
     				}
     				else {
     					Toast toast = Toast.makeText(getApplicationContext(), 
     							"Connect your device to the internet \nin order to display messages", 
     							Toast.LENGTH_LONG);
     					toast.show();
     				}
     	}
     	
     	
	// Create Message Button --> hidden because with the Action Bar Menu it's no longer needed
//		Button btn_create = (Button) findViewById(R.id.btn_create);
//		btn_create.setOnClickListener (new View.OnClickListener() {
//			public void onClick(View v) {
//				Intent i = new Intent(getApplicationContext(),SendNotification.class);
//		        startActivity(i);
//		        }
//		 	});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_notification, menu);
		return true;
	}

	// Action Bar Menu
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                openEdit();
                return true;
            case R.id.action_log:
                openLog();
                return true;
//            case R.id.action_settings:
//                openSettings();
//                return true;
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                openHome();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    // methods called by the Icons in the Action Bar
    public void openEdit(){
    	Intent i = new Intent(getApplicationContext(),SendNotification.class);
        startActivity(i);
    }
    
    public void openLog(){
    	Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
        startActivity(i);
    }
    
    public void openHome(){
    	Intent i = new Intent(getApplicationContext(),MainMenu.class);
        startActivity(i);
    }
    
    // check if there is an internet connection available
    public static boolean getConnectivityStatus(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	 
	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (null != activeNetwork) {
	    	return true;
	    }
	    return false;
	}
    
    // save notifications into local storage
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // save the ListArray called als into local storage
        outState.putStringArrayList(KEY, als);
    }
}
