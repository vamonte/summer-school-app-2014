package com.example.universityapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

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
  
        ImageButton btnVotes = (ImageButton)findViewById(R.id.imageButtonVotes);
        
        btnVotes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Votes.class);
				startActivity(i);
			}
        	
        });
        ImageButton btnContact = (ImageButton)findViewById(R.id.imageButtonContacts);
        
        btnContact.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),ContactList.class);
				startActivity(i);
			}
        	
        });
        
        ImageButton btnCalendar = (ImageButton)findViewById(R.id.imageButtonCalendar);
        
        btnCalendar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Month_view.class);
				startActivity(i);
			}
        	
        });
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main_menu, menu);
      return true;
    } 
    

@Override
public boolean onOptionsItemSelected(MenuItem item) {

    switch(item.getItemId())
    {
    case R.id.disconect:
    	SharedPreferences.Editor editor = getSharedPreferences("user_pref",MODE_PRIVATE).edit();
		 editor.putString("user_connected_pk", null);
		 editor.commit();
		 Intent main = new Intent(getApplicationContext(), Login.class);
		 startActivity(main);
    }
    return super.onOptionsItemSelected(item);
}
}