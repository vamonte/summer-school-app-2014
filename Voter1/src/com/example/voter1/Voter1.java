package com.example.voter1;

import com.example.voter1.Voter1;
import com.example.voter1.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class Voter1 extends ActionBarActivity {

	 private RadioGroup radioChoiceGroup;
     private RadioButton radioButton;  
     private Button btnDisplay;
     private String vote;
     
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter1);
        Parse.initialize(this, "yMpYNZXO0j1CwyAdC8VCSNiCaDJ7D9gSFzaNVNnm", "OVzuZXDpu9kcNGzkDYIAMoRvURgQ0M4oY40Zbx1e");
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
                	Toast.makeText(Voter1.this,
                			radioButton.getText(), Toast.LENGTH_SHORT).show();
                
                	ParseObject parse = new ParseObject("Students");
                	parse.put("answer", radioButton.getText());
                	parse.saveInBackground();
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
    
 // Check if there is an Internet connection available
 	// code from https://github.com/viralpatel/android-network-change-detect-example/
 	//           blob/master/src/net/viralpatel/network/NetworkUtil.java
    // Code from Kathrin
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
