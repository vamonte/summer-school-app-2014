package com.example.universityapplication;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;



import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Votes extends ActionBarActivity {

	 private RadioGroup radioChoiceGroup;
     private RadioButton radioButton;  
     private Button btnDisplay;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter1);
        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.voter1, menu);
        return true;
    }

    public void addListenerOnButton() {
        
        radioChoiceGroup = (RadioGroup) findViewById(R.id.rdbGp1);
        btnDisplay = (Button) findViewById(R.id.button1);
 
        btnDisplay.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
                // get selected radio button from radioGroup
                int selectedId = radioChoiceGroup.getCheckedRadioButtonId();
 
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                if(getConnectivityStatus(getApplicationContext())){
                	Toast.makeText(Votes.this,
                			"You choose " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                	
                	SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			        String server = setting_prefs.getString("server_address", null);
			        String addr = new StringBuilder().append("http://").append(server).append("/question/answer/").toString();
					
                	
			        AsyncHttpClient client= new AsyncHttpClient();
			        RequestParams params = new RequestParams();
			        
			        params.put("answer", radioButton.getText());
			        client.post(addr, params, new AsyncHttpResponseHandler() {
						
						// if successful, go to the display notification activity
						@Override
						public void onSuccess(int arg0, Header[] arg1, byte[] response) {
							try {
								JSONObject obj = new JSONObject(new String(response));
								String result = obj.getString("result");
								String body = obj.getString("body");
								
								if(result.equals("success")){
									Toast.makeText(getApplicationContext(), "Answer saves.", Toast.LENGTH_LONG).show();
								}else{
									Toast.makeText(getApplicationContext(), body, Toast.LENGTH_LONG).show();
								}
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						// if not successful, stay on current page, display error message
						@Override
						public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
							Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
							
						}
					});

                }
                else{
                Toast toast = Toast.makeText(getApplicationContext(), 
                      "Connect your device to the internet\n in order to send your answer", 
                      Toast.LENGTH_LONG);
                      toast.show();
                }
 
            }
 
        });
 
    }
    

 	public static boolean getConnectivityStatus(Context context) {
 	    ConnectivityManager cm = (ConnectivityManager) context
 	            .getSystemService(Context.CONNECTIVITY_SERVICE);
 	 
 	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
 	    if (null != activeNetwork) {

 	    	return true;
 	    }

 	    return false;
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
}
