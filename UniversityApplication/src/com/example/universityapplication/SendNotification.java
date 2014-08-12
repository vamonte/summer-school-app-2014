package com.example.universityapplication;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SendNotification extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_notification);
		
		// Activate Up Button in Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Function for Send-Button (Create Message)
     	Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener (new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		// check if there is internet connection available
        		if(getConnectivityStatus(getApplicationContext())){
        		
        			// Create Strings from Text Input
        			EditText et_msg = (EditText) findViewById(R.id.txtfield_message);
        			String message = et_msg.getText().toString();
    			
        			// before sending, check if the message contains content
        			if (message.length()>0){
        				
        				// get username that has been entered at login
        				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    					String user = prefs.getString("user_connected_pk", "1");
    					
    					// connect to the server
    					AsyncHttpClient client = new AsyncHttpClient();
    					RequestParams params = new RequestParams();
    					// add data
    					params.put("user", user);
    					params.put("message", message);
    					// post it to the server within another activity
    					
    					SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    			        String server = setting_prefs.getString("server_address", null);
    			        String addr = new StringBuilder().append("http://").append(server).append("/notifications/add").toString();
    					
    					client.post(addr, params, new AsyncHttpResponseHandler() {
							
    						// if successful, go to the display notification activity
							@Override
							public void onSuccess(int arg0, Header[] arg1, byte[] response) {
								Toast.makeText(getApplicationContext(), new String(response), Toast.LENGTH_LONG);
								Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
		    					startActivity(i);
							}
							
							// if not successful, stay on current page, display error message
							@Override
							public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
								Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG);
								
							}
						});
    					
    					}
        			
        			// if there is no content in the message, toast will be displayed to enter message
        			// it is not possible to send an empty message ;-)
        			else{
        				Toast toast = Toast.makeText(getApplicationContext(), "Enter your message", Toast.LENGTH_LONG);
        				toast.show();
    					}
        			}
        		    // if there is no internet connection, message will be toasted
        			else{
        				Toast toast = Toast.makeText(getApplicationContext(), 
        						"Connect your device to the internet \nin order to send your message", 
        						Toast.LENGTH_LONG);
        				toast.show();
        				}
    				} 
             });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_notification, menu);
		return true;
	}


	
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
		
	// Check if there is an Internet connection available
	// code from https://github.com/viralpatel/android-network-change-detect-example/
	//           blob/master/src/net/viralpatel/network/NetworkUtil.java
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
