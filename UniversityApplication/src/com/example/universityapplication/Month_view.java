package com.example.universityapplication;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Month_view extends Activity implements Runnable {


	//-----------------
EventList EL;
EventList ELbis;
    //-----------------



//Attributes
public EventList ListEv=new EventList();
public ArrayList<Day> AD=new ArrayList<Day>();
public String buffer="";
public Day D=null;
private static final String TAG_TITLE = "title";
private static final String TAG_LOCATION= "location";
private static final String TAG_TEACHER = "teacher";
private static final String TAG_STARTH = "start_hour";
private static final String TAG_ENDH = "end_hour";
private ProgressDialog mprogressDialog;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_month_view);
		
		mprogressDialog = new ProgressDialog(this);
		// Msg progressBar
		mprogressDialog.setMessage("Download...");
		// Title progressBar
		mprogressDialog.setTitle("Calendar");
		// Style progressBar (STYLE_HORIZONTAL or STYLE_SPINNER)
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// Display progressBar
		mprogressDialog.show();

		Thread thread = new Thread(this);
		thread.start();   
		//function allowing to request the serve 
		//---------------------------------------------------------------------------------
		RequestWS();
		//---------------------------------------------------------------------------------
		
		//Instantiate the calendarView
		CalendarView cd=(CalendarView) findViewById(R.id.calendarView1);

		//Create some local Event for testing some functions 
		//--------------------------------------------------------------------------------------------------
		/*Event E=new Event("Problem based learning","S114","MrX","8.30","9.30");
		Event E1=new Event("Mobile phone programing","S115","MrY","9.30","10.30");
		Event E2=new Event("Alternative Energy","S116","MrZ","10.30","11.30");
		//---
		Event E3=new Event("Problem based Learning","S117","MrA","12.30","13.30");
		Event E4=new Event("How to work in teams","S118","MrB","13.30","14.30");
		Event E5=new Event("Android programing","S119","MrC","14.30","15.30");
		
		//Storing in EventList 
		EL=new EventList();
		EL.add(E);
		EL.add(E1);
		EL.add(E2);
		
		ELbis=new EventList();
		ELbis.add(E3);
		ELbis.add(E4);
		ELbis.add(E5);
		
		//Storing List in Day
		Day D=new Day();
		D.setD(EL);
		D.setDate("2014-08-12");
		
		Day D1=new Day();
		D1.setD(ELbis);
		D1.setDate("2014-08-14");
		
	    //Add object to the day list
		AD.add(D);
		AD.add(D1);
		
		
		*/
		//-------------------------------------------------------------------------------------------------------------
		/*ListEv.add(E);
		ListEv.add(E1);
		ListEv.add(E2);
		*/
	 
		//GregorianCalendar C=new GregorianCalendar(2014, 7, 2);
		//GregorianCalendar Cbis=new GregorianCalendar(2014, 7, 18);
		//C.getTimeInMillis();
		//cd.setMinDate(C.getTimeInMillis());
		//cd.setMinDate(Cbis.getTimeInMillis());
		
		
		//Listen when we click on a day 
        cd.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				//Month begin at 0 and I want 1 
				month++;
				String Date="";
				//Create String for find the good day from the server 
				if(month>9 && dayOfMonth>9){
					Date=year+"-"+month+"-"+dayOfMonth;
				}
				else if(month<10 && dayOfMonth>9){
					Date=year+"-0"+month+"-"+dayOfMonth;
				}
				else if(month>9 && dayOfMonth<10){
					Date=year+"-"+month+"-0"+dayOfMonth;
				}
				else if(month<10 && dayOfMonth<10){
					Date=year+"-0"+month+"-0"+dayOfMonth;
				}
				else{Date="Error";}
				
				//Toast.makeText(getApplicationContext(),"Click date : " + Date, Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), AD.get(0).getDate() + "  " + Date, Toast.LENGTH_LONG).show();
				
				Log.i("Inf",Integer.toString(AD.size()));

			    for(int i = 0; i < AD.size(); i++)
			    {
					 Log.i("Inf","N?=" + i + " Jour : " + AD.get(i).getDate());
					 Log.i("Inf","Nom prof " + AD.get(i).getD().get(0).getTeacherName());
			            
			       //Finding the good day 
			    	if(AD.get(i).getDate().compareTo(Date)==0){
			    
			    	  Log.i("Inf","trouv? :" + AD.get(i).getDate());
			    	  //Toast.makeText(getApplicationContext(),AD.get(i).getDate()+"OK", Toast.LENGTH_LONG).show();
			    	  
			    	  //Store the EventList of the day in a local list
			    	  ListEv=AD.get(i).getD();
			    	  
			    	  //Send it to Day_View 
					  Intent myIntent = new Intent(Month_view.this,Day_view.class);
					  myIntent.putExtra("identifiantListe",(Parcelable)ListEv);
					  startActivity(myIntent);				  					  				
			       												}
		       
					 }  
				

			}
		});
	}

//Display on spinner 
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int i = msg.what;
			switch (i) 
			{ 
			//Message display
				case 1: mprogressDialog.setMessage("Download..."); break;  
			//Delete spinner 	
				default:mprogressDialog.dismiss();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.month_view, menu);
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
	
	
	//------Function for request the web service 
	public void RequestWS(){
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
         // Make request to Web Service call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        
        SharedPreferences prefs = getSharedPreferences("user_pref",MODE_PRIVATE); 
        String pk = prefs.getString("user_connected_pk", null);

		SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String server = setting_prefs.getString("server_address", null);
        String addr = new StringBuilder().append("http://").append(server).append("/contact/students/").append(pk).append("/events").toString();

        client.get(addr,params,new AsyncHttpResponseHandler() {          		           
        	// When the response returned is ok ! 	
        	
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
					
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] response) {
					//Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
				    //Using a try-catch 
		           try {
		        	   //Create a JSONObject with the String "response" (parameter)
		                	 JSONObject obj = new JSONObject(new String(response));              	 
									try{
										//Getting back all the events 
					    				JSONArray JA= obj.getJSONArray("events");
					    				//Toast using for debugging
			             				//Toast.makeText(getApplicationContext(), "Success JSON ARRAY", Toast.LENGTH_LONG).show();
					     				//Toast.makeText(getApplicationContext(), JA.toString() , Toast.LENGTH_LONG).show();		         			
										         
					     							//Fill the local list of event  for each event
					     							for(int i=0;i< JA.length();i++)
										            {
										           	Event LocalEv=new Event();
										         	JSONObject student=JA.getJSONObject(i);
										         	JSONObject fields = student.getJSONObject("fields");
										         	//Toast.makeText(getApplicationContext(),student.toString(), Toast.LENGTH_LONG).show();
										         	
										         	//Get Title, Location,...
										         	LocalEv.setTitle(fields.getString(TAG_TITLE));
										         	LocalEv.setLocation(fields.getString(TAG_LOCATION));
										         	LocalEv.setTeacherName(fields.getString(TAG_TEACHER));
										         	//---------------------
										         	
										         	//Create date 
										            Date date = null;
										            Date datebis=null;
										            
										            //converting the date descended from the server
										    		try {
										    			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(fields.getString(TAG_STARTH));
										    			datebis= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(fields.getString(TAG_ENDH));
										    		} catch (ParseException e) {
										    			// TODO Auto-generated catch block
										    			e.printStackTrace();
										    			Toast.makeText(getApplicationContext(), "Date conversion failed", Toast.LENGTH_LONG).show();
										    		}
										    		//Change the format of the date 
										            String Stime = new SimpleDateFormat("H:mm").format(date);
										            String Etime = new SimpleDateFormat("H:mm").format(datebis);
										            String DayDate=new SimpleDateFormat("yyyy-MM-dd").format(date);
										            
										            Log.i("Inf","DayDate:"+DayDate);//---------------------
										            
										            LocalEv.setStartH(Stime);
										         	LocalEv.setEndH(Etime);
										           
										         	//Algo allowing to award the listofEvent to the good Day
										         	
										         	//For the first day
										            if(buffer.compareTo("")==0)
										            {
										            	buffer=DayDate;
										            	Log.i("Inf",buffer);//-------------------
										            	D=new Day();
										            	Log.i("Inf","Creation D");//--------------------
										            	D.setDate(DayDate);
										            	D.getD().add(LocalEv);
										            	Log.i("Inf","Chgt parametre");//-------------------
										            }
										            //If nb_day >= 1
										           else{
										            	//if the date doesn't change 
										            	if(buffer.compareTo(DayDate)==0)
										            	{
										            		//Continue to fill the arrayList
										            		D.getD().add(LocalEv);
										            		Log.i("Inf","Remplissage du jour en cours"+D.getDate());//-------------------
										            	}
										            	//if the date change 
										            	else{
										            		
										            		AD.add(D);
										            		Log.i("Inf","Rajout du jour dans l'arraylist");//-------------------
										                    Log.i("Inf",Integer.toString(AD.size()));
												            Log.i("Inf",AD.get(0).getDate());
										            		
												            buffer=DayDate;
										            		Log.i("Inf","R?ecriture du nouveau buffer: "+buffer);//-------------------
										               		D=null;
										            		D=new Day();
										            		D.setDate(DayDate);
										            		D.getD().add(LocalEv);
										            		Log.i("Inf","New Day"+D.getDate()+"Taille AL"+Integer.toString(D.getD().size()));
										            		Log.i("Inf","NouveauJourCr?er");
										            		
										            		if(i==JA.length()-1)
										            		{
										            			AD.add(D);
										            		}
										            		
										            	    }
										            	
										            	
										            }
										          
										  				
										            
										            //Toast.makeText(getApplicationContext(), "RemplissageDayOk", Toast.LENGTH_LONG).show();
										         	//------------------------
										       		
										            
										         	ListEv.add(LocalEv);
										         					
										         	//String t= fields.getString("user_name");
										         	//Toast.makeText(getApplicationContext(),t, Toast.LENGTH_LONG).show();tv.setText(t);
										         	//Toast.makeText(getApplicationContext(),LocalEv.getTitle() + "-" + LocalEv.getLocation() + "-" + LocalEv.getTeacherName()+"-"+LocalEv.getStartH()+"-"+LocalEv.getEndH(), Toast.LENGTH_LONG).show();				
										            }
										         	//Toast.makeText(getApplicationContext(),"PARSIng TERMINE", Toast.LENGTH_LONG).show();
										         			    
						         				
										     } catch(Exception e){
										         				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
										     					}
										         			
										                      										               
				        	  } catch (JSONException e) {
								  // TODO Auto-generated catch block
				        		  //Problem to parse the JSON
				        		  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
							 
			            	  							}
					
				}
        	
																});

	}


	@Override
	public void run() {
		try {
			Thread.sleep(1500);
			//Write "Download" in the spinner 
			handler.sendEmptyMessage(1);
			Thread.sleep(1500);
			//Close the spinner 
			handler.sendEmptyMessage(2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
