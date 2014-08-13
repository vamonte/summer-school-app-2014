package com.example.universityapplication;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StaffAdapter extends ArrayAdapter<Staff> {
	
	//Attribute
	private ArrayList<Staff> objects;

	//Constructor
	public StaffAdapter(Context context, int textViewResourceId, ArrayList<Staff> objects){
		super(context, textViewResourceId, objects);
		this.objects=objects;	
	}
	
	//Define the view of our listView
	public View getView(int position, View convertView, ViewGroup parent){
		View v=convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//XML file define in the folder layout
			v = inflater.inflate(R.layout.student_list, null);
		}
		
		//Create object staff for each item
		Staff s=objects.get(position);
		
		if (s != null) {
            //Instantiate TV
			TextView Name = (TextView) v.findViewById(R.id.Name);
			TextView Prenom = (TextView) v.findViewById(R.id.Prenom);
	
			//Testing that TV really exist
			if (Name != null){
				Name.setText(s.getName());
			}
			if (Prenom != null){
				Prenom.setText(s.getPrenom());
			}
		}
		
		return v;
	}
}
