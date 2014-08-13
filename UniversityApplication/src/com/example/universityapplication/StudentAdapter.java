package com.example.universityapplication;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class StudentAdapter extends ArrayAdapter<Student> {
	
	//Attribute
	private ArrayList<Student> objects;

	//Constructor
	public StudentAdapter(Context context, int textViewResourceId, ArrayList<Student> objects){
		super(context, textViewResourceId, objects);
		this.objects=objects;	
	}
	
	//Define the view of our list
	public View getView(int position, View convertView, ViewGroup parent){
		View v=convertView;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//XML File define in the folder layout
			v = inflater.inflate(R.layout.student_list, null);
		}
		
		Student s=objects.get(position);
		
		if (s != null) {

			TextView Name = (TextView) v.findViewById(R.id.Name);
			TextView Prenom = (TextView) v.findViewById(R.id.Prenom);
	
			
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
