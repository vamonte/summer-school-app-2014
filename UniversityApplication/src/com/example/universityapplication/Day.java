package com.example.universityapplication;

import java.util.ArrayList;

public class Day {

	//Attribute
	private String Date;
	//private ArrayList<Event> D = new ArrayList<Event>();
	private EventList D=new EventList();
	
	//getters
	public String getDate() {return Date;}
	public EventList getD() {return D;}
	//public ArrayList<Event> getD() {return D;}
	
    //Setters
	public void setDate(String date) {Date = date;}
	public void setD(EventList d) {D = d;}
	//public void setD(ArrayList<Event> d) {D = d;}
    
	//Constructor
	public Day(){}
}
