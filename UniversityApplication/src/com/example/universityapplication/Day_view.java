package com.example.universityapplication;




import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Day_view extends ListActivity{

	//Attribut 
	EventList maliste;
	int Annee;
	int Mois;
	int Jour;
	private EventAdapter mAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_view);
		//ListView already existing in ListActivity
		ListView Lv=getListView();
		//Toast 
		//Toast.makeText(getApplicationContext(), "OK DAY_VIEW",Toast.LENGTH_LONG).show();
		
		//Receive from Month_View
	    Bundle b    = getIntent().getExtras();
	    //Store data in a list
	    maliste    = b.getParcelable("identifiantListe");
	    
	    //Creating an eventAdapter for use object in listview
		mAdapter=new EventAdapter(this,R.layout.activity_day_view,maliste);
		//displaying
		setListAdapter(mAdapter);
		
		
		//Click on a contact
		Lv.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			  /*  Toast.makeText(getApplicationContext(),
			      "Click ListItem Number " + position, Toast.LENGTH_LONG)
			      .show();
			    */
				//Create an intent for send data to Main_Activity  
				Intent i=new Intent(getApplicationContext(),DetailsCalendar.class);
				/*i.putExtra("Title", E.getTitle());
				i.putExtra("Location", E.getLocation());
				i.putExtra("StartH", E.getStartH());
				i.putExtra("EndH", E.getEndH());
				i.putExtra("Teacher", E.getTeacherName());*/
				
				//Sending of the information
				i.putExtra("Title", maliste.get(position).getTitle());
				i.putExtra("Location", maliste.get(position).getLocation());
				i.putExtra("StartH", maliste.get(position).getStartH());
				i.putExtra("EndH", maliste.get(position).getEndH());
				i.putExtra("Teacher", maliste.get(position).getTeacherName());
				startActivity(i);
			  }
			}); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day_view, menu);
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
