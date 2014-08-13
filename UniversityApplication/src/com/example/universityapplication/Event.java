package com.example.universityapplication;

import android.os.Parcel;
import android.os.Parcelable;


public class Event implements Parcelable  {

//Attributes
	private String title;
	private String Location;
	private  String TeacherName;
	private String StartH;
	private String EndH;
	
//Getter & Setter	
	public String getStartH() {return StartH;}
	public String getEndH() {return EndH;}
	public String getTitle() {return title;}
	public String getLocation() {return Location;}
	public String getTeacherName() {return TeacherName;}	

	public void setStartH(String startH) {this.StartH = startH;}
	public void setEndH(String endH) {this.EndH = endH;}
	public void setTitle(String title) {this.title = title;}
	public void setLocation(String location) {this.Location = location;}
	public void setTeacherName(String teacherName) {this.TeacherName = teacherName;}
	
//Constructors
	public Event()
	{		
	}
	public Event(String Title,String Locat,String Name,String Starth,String Endh){
		title=Title;
		Location=Locat;
		TeacherName=Name;
		StartH=Starth;
		EndH=Endh;
	}
//Second constructor call for the "Deparcelablisation"
    public Event(Parcel in)
    {
        this.getFromParcel(in);
    }
 
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Event createFromParcel(Parcel in)
        {
            return new Event(in);
        }
 
        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    //Writing data in or object "Parcelable"
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.StartH);
        dest.writeString(this.EndH);
        dest.writeString(this.title);
        dest.writeString(this.Location);
        dest.writeString(this.TeacherName);   
    }
    
    //Getting data writing before
    public void getFromParcel(Parcel in)
    {
        this.setStartH(in.readString());
        this.setEndH(in.readString());
        this.setTitle(in.readString());
        this.setLocation(in.readString());
        this.setTeacherName(in.readString());

    }

}
