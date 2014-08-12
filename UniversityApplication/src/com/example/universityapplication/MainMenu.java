package com.example.universityapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

// Second Activity = MAIN MENU
public class MainMenu extends ActionBarActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);     
        getActionBar();
        // Identify the buttons by resource ID
        ImageButton btnNews = (ImageButton) findViewById(R.id.imageButtonNews);
        
        
        
        // Set the listener for the NEWS button
        btnNews.setOnClickListener(new OnClickListener() 
        {	
			@Override
			public void onClick(View v) 
			{
				// Start the third activity : NEWS
				Intent i = new Intent(getApplicationContext(),News.class);
				startActivity(i);
			}
        });
        ImageButton btnChat = (ImageButton)findViewById(R.id.imageButtonPmessages);
        
        btnChat.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),DisplayNotification.class);
				startActivity(i);
			}
        	
        });
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
        case R.id.action_settings:
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        
        case R.id.disconect:
        	 SharedPreferences prefs = getPreferences(MODE_PRIVATE); 
        	 SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
    		 editor.putString("user_connected_pk", null);
    		 
    		 Intent main = new Intent(getApplicationContext(), Login.class);
    		 startActivity(main);
        }
        return super.onOptionsItemSelected(item);
    }
}