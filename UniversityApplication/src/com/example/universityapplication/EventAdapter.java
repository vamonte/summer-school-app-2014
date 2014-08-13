package com.example.universityapplication;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class EventAdapter extends ArrayAdapter<Event> {
	
	//Attribute
	private ArrayList<Event> objects;

	//Constructors
	public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> objects){
		super(context, textViewResourceId, objects);
		this.objects=objects;	
	}
	
	//Get the view of our listView
	public View getView(int position, View convertView, ViewGroup parent){
		View v=convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//Even_list is an XML file and save in the folder : "layout"
			v = inflater.inflate(R.layout.event_list, null);
		}
		
	    //Create object Event for each position
		Event e=objects.get(position);
		
		//Test if the item is not empty
		if (e != null) {
			
			//Get our textView define in event_list.xml
			TextView Hour = (TextView) v.findViewById(R.id.H);
			TextView Course = (TextView) v.findViewById(R.id.Cours);
	
			//if the textview aren't use and really exist
			if (Hour != null){
				//Display Start Hour and End hour
				Hour.setText(e.getStartH()+"-"+e.getEndH());
			}
			//as above 
			if (Course != null){
				//Display the course's title
				Course.setText(e.getTitle());
			}
		}
		
		return v;
	}
}
