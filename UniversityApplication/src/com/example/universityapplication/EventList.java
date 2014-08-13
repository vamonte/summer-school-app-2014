package com.example.universityapplication;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class EventList extends ArrayList<Event> implements Parcelable {
	
	//Constructor
	public EventList(){}	
    public EventList(Parcel in) { this.getFromParcel(in);}
 
    
    //Function allowing the "Parcelabisation"
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public EventList createFromParcel(Parcel in)
        {
            return new EventList(in);
        }
 
        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
   //Obligation to use it because it's an Override function
    @Override
    public int describeContents() {return 0;}
 
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Size for the FOR Loop
        int size = this.size();
        //Writing the size for sending 
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
        	//Create local variable 
            Event ev = this.get(i); 
            //Write data for sending
            dest.writeString(ev.getStartH());
            dest.writeString(ev.getEndH());
            dest.writeString(ev.getTitle());
            dest.writeString(ev.getLocation());
            dest.writeString(ev.getTeacherName());
        }
    }
 
    public void getFromParcel(Parcel in)
    {
        //List should be empty for her filling
    	//"this." is the arrayList
        this.clear();
 
        //Getting the size
        int size = in.readInt();
 
        //Fill this 
        for(int i = 0; i < size; i++)
        {
            Event ev = new Event();
            ev.setStartH(in.readString());
            ev.setEndH(in.readString());
            ev.setTitle(in.readString());
            ev.setLocation(in.readString());
            ev.setTeacherName(in.readString());
            this.add(ev);
        }
 
    }

}
