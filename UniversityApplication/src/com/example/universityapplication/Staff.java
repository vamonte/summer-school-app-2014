package com.example.universityapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Staff implements Parcelable {

//Attributes
private String name;
private String prenom;
private String university;
private String sexe;
private String telephone;
private String email;

//Constructor
public Staff(){	
}

public Staff(String n, String p,String u,String s,String t,String e){
	this.name=n;
	this.prenom=p;
	this.university=u;
	this.sexe=s;
	this.telephone=t;
	this.email=e;

}

//Getters
public String getName() {return name;}
public String getPrenom() {return prenom;}
public String getEmail() {return email;}
public String getTelephone() {return telephone;}
public String getUniversity() {return university;}
public String getSexe() {return sexe;}
//Setter
public void setName(String name) {this.name = name;}
public void setPrenom(String prenom) {this.prenom = prenom;}
public void setEmail(String email) {this.email = email;}
public void setTelephone(String telephone) {this.telephone = telephone;}
public void setUniversity(String university) {this.university = university;}
public void setSexe(String sexe) {this.sexe = sexe;}

public Staff(Parcel in)
{
    this.getFromParcel(in);
}

@SuppressWarnings("rawtypes")
public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
{
    public Staff createFromParcel(Parcel in)
    {
        return new Staff(in);
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

//Writing data in or object Parcelable
@Override
public void writeToParcel(Parcel dest, int flags)
{
    dest.writeString(this.name);
    dest.writeString(this.prenom);
    dest.writeString(this.university);
    dest.writeString(this.sexe);
    dest.writeString(this.telephone);  
    dest.writeString(this.email);
}

//Getting data an create new object
public void getFromParcel(Parcel in)
{
    this.setName(in.readString());
    this.setPrenom(in.readString());
    this.setUniversity(in.readString());
    this.setSexe(in.readString());
    this.setTelephone(in.readString());
    this.setEmail(in.readString());

}

}
