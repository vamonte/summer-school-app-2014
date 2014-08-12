package com.example.universityapplication;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Main Activity = LOG IN PAGE
@SuppressLint("NewApi") public class Login extends ActionBarActivity 
{
   
	
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar();
        
        
        // Declare the two edit text for the Username and Password
        final EditText edit_username = (EditText)findViewById(R.id.editusername);
        final EditText edit_password = (EditText)findViewById(R.id.editpassword);
        
        // Declare the preferences used to manage user (re)connection
        SharedPreferences prefs = getPreferences(MODE_PRIVATE); 
        String pk = prefs.getString("user_connected_pk", null);
        String username = prefs.getString("user_connecte_name", null);
        
        // If the preferences are already set
        if(pk != null)
        {
        	// Then go to the main menu of the application
        	goToMainMenu();
        }
        else
        {
        	// If the username is already known
        	if(username != null)
        	{
        		edit_username.setText(username);
        	}
        }
        
        // Set the listener for the button : Connect
        Button b = (Button)findViewById(R.id.button1);
        b.setOnClickListener(new Button.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				// Get the user name and the password
				RequestParams params = new RequestParams();
				if(!edit_username.getText().toString().isEmpty() && !edit_password.getText().toString().isEmpty())
				{
					params.put("user_name",  edit_username.getText().toString());
					params.put("password",  edit_password.getText().toString());
					authRequest(params);
				} 
				// If the fields are empty
				else 
				{
					// Then display a toast message : Username and password can't be empty
					Toast.makeText(getApplicationContext(), "Username and password can't be empty.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
    
	private void authRequest(RequestParams params)
    {
    	AsyncHttpClient client = new AsyncHttpClient();
    	// Send the connection parameters to the server
    	SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String server = setting_prefs.getString("server_address", null);
        String addr = new StringBuilder().append("http://").append(server).append("/contact/authenticate").toString();
    	client.post(addr, params, new AsyncHttpResponseHandler() {
    		
			@Override
			// If the connection fails
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,Throwable arg3)
			{
				// Then display a toast message : Network error
				Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
			}

			@Override
			// If the connection works
			public void onSuccess(int arg0, Header[] arg1, byte[] response) 
			{
				try 
				{
					// Get the response from the server as an JSON object
					JSONObject obj = new JSONObject(new String(response));
					Boolean is_success_auth = isSuccessAuth(obj);
					// If the connection parameters are accepted
					if(is_success_auth)
					{
						// Connect, enable the user to stay connected and  go to the main menu
						saveConnectedUser(obj);
						goToMainMenu();
					}else
					{
						// Then display a toast message : ErrorMsg given by the test
						Toast.makeText(getApplicationContext(), getErrorMsg(obj), Toast.LENGTH_LONG).show();
					}
				} 
				// If there is an error with the JSON object
				catch (JSONException e) 
				{	
					// Then display a toast message : Application error
					Toast.makeText(getApplicationContext(), "Application error", Toast.LENGTH_LONG).show();
				}
			}
    	});	
    }
    
	protected void goToMainMenu()
    {	
    	// Start the second activity : MAIN MENU
		Intent i = new Intent(getApplicationContext(),MainMenu.class);
		startActivity(i);	
    }
    
    protected void saveConnectedUser(JSONObject obj){
    	try 
    	{
			JSONObject body = obj.getJSONObject("body");
			String pk = body.getString("pk");
			
			JSONObject fields = obj.getJSONObject("fields");
			String user_name = fields.getString("user_name");
			
			 SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
			 editor.putString("user_connected_pk", pk);
			 editor.putString("user_connecte_name", user_name);
			 editor.apply();
		}
    	
    	catch (JSONException e) 
    	{
			e.printStackTrace();
		}
    }
    
    protected String getErrorMsg(JSONObject obj)
    {
    	String error_msg = null;
    	try 
    	{
    		error_msg = (String)obj.getString("body");
		} 
    	catch (JSONException e) 
    	{
			error_msg = "Application error";
		}
		return error_msg;
    }
    
    protected Boolean isSuccessAuth(JSONObject obj)
    {

    	String status = null;
    	Boolean success = false;
    	try 
    	{
			status = (String) obj.get("status");
		} 
    	catch (JSONException e) 
    	{
			status = "error";
		}
    	if(status.equals("success"))
    	{
    		success = true;
    	}
    	return success;
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

