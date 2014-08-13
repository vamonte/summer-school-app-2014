package com.example.universityapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailsCalendar extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailcalendar);
        
        //Getting data from Day_View
        Bundle b = getIntent().getExtras();
        //Store data in local variable 
        String Title = b.getString("Title");
        String Location=b.getString("Location");
        String StartH=b.getString("StartH");
        String EndH=b.getString("EndH");
        String Teacher=b.getString("Teacher");

	        
       

        //Hours H=new Hours("8.00","9.00");
        //Event E=new Event("History","114-4-133","Mr. X",H);
        
        //instantiate all the textview
        TextView tv=(TextView) findViewById(R.id.Title);
        TextView tv1=(TextView) findViewById(R.id.TitleVariable);
        TextView tv2=(TextView) findViewById(R.id.Teacher);
        TextView tv3=(TextView) findViewById(R.id.TeacherVariable);
        TextView tv4=(TextView) findViewById(R.id.Hours);
        TextView tv5=(TextView) findViewById(R.id.HoursVariable);
        TextView tv6=(TextView) findViewById(R.id.Location);
        TextView tv7=(TextView) findViewById(R.id.LocationVariable);
        
        //Underline with content + setText for writing our local variable  
        //-----------------
        SpannableString content = new SpannableString("Title:");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setText(content);
        tv1.setText("    "+Title);//E.getTitle());
        //-----------------
        SpannableString content2 = new SpannableString("Location:");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tv2.setText(content2);
        tv3.setText("    "+Location);//E.getLocation());
        //------------------
        SpannableString content4 = new SpannableString("Teacher:");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tv4.setText(content4);
        tv5.setText("    "+Teacher);//E.getTeacherName());
        //------------------
        SpannableString content6 = new SpannableString("Hours:");
        content6.setSpan(new UnderlineSpan(), 0, content6.length(), 0);
        tv6.setText(content6);
        tv7.setText("    "+StartH + " - " + EndH);//E.getHour().getStartHour() + "-" + E.getHour().getEndHour());
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
