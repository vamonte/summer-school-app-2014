package com.example.universityapplication;


public class Student /*implements Parcelable*/ {

private String name;
private String prenom;
private String university;
private String sexe;

//constructor
public Student(String n, String p,String u,String s){
	this.name=n;
	this.prenom=p;
	this.university=u;
	this.sexe=s;
}
public Student()
{		
}
//Getter
public String getName() {return name;}
public String getPrenom() {return prenom;}
public String getUniversity() {return university;}
public String getSexe() {return sexe;}
//Setter
public void setName(String name) {this.name = name;}
public void setPrenom(String prenom) {this.prenom = prenom;}
public void setUniversity(String university) {this.university = university;}
public void setSexe(String sexe) {this.sexe = sexe;}

/*
//Second constructor call for the "Deparcelablisation"
public Student(Parcel in)
{
    this.getFromParcel(in);
}


@SuppressWarnings("rawtypes")
public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
{
    public Student createFromParcel(Parcel in)
    {
        return new Student(in);
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

@Override
//Writing data in or object Parcelable
public void writeToParcel(Parcel dest, int flags)
{
    dest.writeString(this.name);
    dest.writeString(this.prenom);
    dest.writeString(this.university);
    dest.writeString(this.sexe);   
}

public void getFromParcel(Parcel in)
{
    this.setName(in.readString());
    this.setPrenom(in.readString());
    this.setUniversity(in.readString());
    this.setSexe(in.readString());
}

*/

}
