package com.example.notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Activate Up Button in Action Bar
         getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        
				
		// Function for Send-Button (Create Message)
		Button btn_create = (Button) findViewById(R.id.btn_create_message);
        btn_create.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SendNotification.class);
                startActivity(i);
            }
        });
        
        // Function for LogView-Button
        Button btn_log = (Button) findViewById(R.id.btn_view_log);
        btn_log.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
                startActivity(i);
            }
        });
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    
    // new onOptionItemSelected method following tutorial on
    // https://developer.android.com/training/basics/actionbar/adding-buttons.html
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
    
    // methods for the Icons in the Action Bar
    public void openEdit(){
    	Intent i = new Intent(getApplicationContext(),SendNotification.class);
        startActivity(i);
    }
    
    public void openLog(){
    	Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
        startActivity(i);
    }  
}
