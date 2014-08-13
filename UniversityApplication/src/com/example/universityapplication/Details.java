package com.example.universityapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Details extends Activity {
	String Tel;
	String Email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		//Getting information from ContactList
	     Bundle b = getIntent().getExtras();
	        String Name = b.getString("Name");
	        String Prenom=b.getString("Prenom");
	        String University=b.getString("University");
	        String Sexe=b.getString("Sexe");
	        Tel=b.getString("telephone");
	        Email=b.getString("email");
	        String flag=b.getString("flag");
	
		//instantiate TView
        TextView tv=(TextView) findViewById(R.id.Name);
        TextView tv1=(TextView) findViewById(R.id.NameVariable);
        TextView tv2=(TextView) findViewById(R.id.Prenom);
        TextView tv3=(TextView) findViewById(R.id.PrenomVariable);
        TextView tv4=(TextView) findViewById(R.id.university);
        TextView tv5=(TextView) findViewById(R.id.universityVariable);
        TextView tv6=(TextView) findViewById(R.id.sexe);
        TextView tv7=(TextView) findViewById(R.id.sexeVariable);
        TextView tv8=(TextView) findViewById(R.id.telephone);
        TextView tv9=(TextView) findViewById(R.id.telephoneVariable);
        TextView tv10=(TextView) findViewById(R.id.email);
        TextView tv11=(TextView) findViewById(R.id.emailVariable);

        //Underline 
        SpannableString content = new SpannableString("Name:");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setText(content);
        tv1.setText("    "+Name);
        //-----------------
        SpannableString content2 = new SpannableString("First Name:");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tv2.setText(content2);
        tv3.setText("    "+Prenom);
        //------------------
        SpannableString content4 = new SpannableString("University:");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tv4.setText(content4);
        tv5.setText("    "+University);
        //------------------
        SpannableString content6 = new SpannableString("Sexe:");
        content6.setSpan(new UnderlineSpan(), 0, content6.length(), 0);
        tv6.setText(content6);
        tv7.setText("    "+Sexe);
        //------------------
        if(flag.compareTo("Staff")==0){        
        SpannableString content8 = new SpannableString("Telephone:");
        content8.setSpan(new UnderlineSpan(), 0, content8.length(), 0);
        tv8.setText(content8);
        tv9.setText("    "+Tel);
        //------------------
        SpannableString content10 = new SpannableString("Email:");
        content10.setSpan(new UnderlineSpan(), 0, content10.length(), 0);
        tv10.setText(content10);
        tv11.setText("    "+Email);
        }
        
        
        tv9.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phoneNb = Tel.toString();
				String phoneNb2 = "tel:" + phoneNb.trim(); //delete eventual spaces in phoneNb
				Intent intent4 = new Intent(Intent.ACTION_DIAL);
				intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
				intent4.setData(Uri.parse(phoneNb2));
				startActivity(intent4);
			}
		});
        
        tv11.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String message = "mailto:" + Email.toString();				
				Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, ""); //objet
				intent.putExtra(Intent.EXTRA_TEXT, ""); //texte du message
				intent.setData(Uri.parse(message));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
				startActivity(intent);
			}
		});
        

        
              
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
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
