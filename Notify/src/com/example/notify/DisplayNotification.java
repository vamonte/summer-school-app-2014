package com.example.notify;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_notification);
		lv = (ListView) findViewById(R.id.lv_notifications);
		
		// Activate Up Button in Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// connect to the server, opens another thread
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get("http://172.17.36.39:8080/notifications", params, new AsyncHttpResponseHandler(){
	
			// if connection fails
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();	
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
                NavUtils.navigateUpFromSameTask(this);
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
}
