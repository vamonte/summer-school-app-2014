package com.example.notify;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class SendNotification extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_notification);
		
		// set up Parse connection
        Parse.initialize(this, "yMpYNZXO0j1CwyAdC8VCSNiCaDJ7D9gSFzaNVNnm", "OVzuZXDpu9kcNGzkDYIAMoRvURgQ0M4oY40Zbx1e");
     		
     // Function for Send-Button (Create Message)
     	Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener (new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		// Check if there is an Internet connection available
    			// if (getConnectivityStatus(Context context)) {
        		if(getConnectivityStatus(getApplicationContext())){
        		
        			// Create Strings from Text Input
        			EditText et_name = (EditText) findViewById(R.id.txtfield_name);
        			EditText et_msg = (EditText) findViewById(R.id.txtfield_message);
        			String name = et_name.getText().toString();
        			String message = et_msg.getText().toString();
        			
        			// get date & time
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        			String date = sdf.format(new Date());
    			
        			// check if there has been content entered into the form before sending
        			if (name.length()>0 && message.length()>0){

    					// Create ParseObject
    					ParseObject parse = new ParseObject("Notification");
    					parse.put("name", name);
    					parse.put("message", message);
    					parse.put("date", date);
    					parse.saveInBackground();
    					Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
    					startActivity(i);
    					}
        			
        			// if there is no content for either name or message, message can't be sent
        			else{
        				Toast toast = Toast.makeText(getApplicationContext(), "Enter your name & message", Toast.LENGTH_LONG);
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

	// original generated code
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    // methods for the Icons in the Action Bar
    public void openEdit(){
    	Intent i = new Intent(getApplicationContext(),SendNotification.class);
        startActivity(i);
    }
    
    public void openLog(){
    	Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
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