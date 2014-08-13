package com.example.universityapplication;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class ContactList extends ListActivity implements Runnable {
	
	//Attribute 
	private StudentAdapter mAdapter;
	private String  drap="";
	private StaffAdapter mAdapterSt;
    private ArrayList<Student> AL_S=new ArrayList<Student>();
    private ArrayList<Staff> AL_St=new ArrayList<Staff>();
    Button btnStaff;
    Button btnStudent;
    private static final String TAG_NOM = "last_name";
    private static final String TAG_PRENOM= "first_name";
    private static final String TAG_UNIVERSITY = "university";
    private static final String TAG_SEXE = "sexe";
    private static final String TAG_TEL = "phone_number";
    private static final String TAG_EMAIL = "email";
    private ProgressDialog mprogressDialog;
        
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        
		mprogressDialog = new ProgressDialog(this);
		// Msg progressBar
		mprogressDialog.setMessage("Download...");
		// Title progressBar
		mprogressDialog.setTitle("Contact");
		// Style progressBar (STYLE_HORIZONTAL or STYLE_SPINNER)
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// Display progressBar
		mprogressDialog.show();

		Thread thread = new Thread(this);
		thread.start();  
        
        RequestWS();
        RequestWStaff();
        
        //Getting List from ListActivity
        ListView Lv=getListView();
        //Instantiate button
        btnStaff=(Button) findViewById(R.id.staff);
        btnStudent=(Button) findViewById(R.id.student);
        
        //Create adapter
    	mAdapter=new StudentAdapter(this,R.layout.activity_contact_list,AL_S);
    	mAdapterSt=new StaffAdapter(this,R.layout.activity_contact_list,AL_St);
    	
    /*	//Create Student for testing 
    	for(int i=0;i<10;i++)
        {
        	Student A=new Student("Name"+i,"Prenom"+i,"University"+i,"Sexe"+i);
        	AL_S.add(A);
        }*/
       //Create Staff for testing
     /*  for(int i=0;i<10;i++)
       {
       	Staff A=new Staff("Nom"+i,"Prenom"+i,"Universite"+i,"Sex"+i,"Telephone"+i,"email"+i);
       	AL_St.add(A);
       }
        */
    	
       	//Event on button
        btnStaff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			    // TODO Auto-generated method stub
				setListAdapter(mAdapterSt);
				//Make the button enabled
				btnStudent.setEnabled(true);
				btnStaff.setEnabled(false);
				//Create a flag
				drap="Staff";
			}
		});
        
        btnStudent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				setListAdapter(mAdapter);
				//Make the button enabled
				btnStudent.setEnabled(false);
				btnStaff.setEnabled(true);
				//Create a flag
				drap="Student";
		
			}
		});
        

        
		
		
		
        //Event on the listView 
		Lv.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
				  
				//Toast.makeText(getApplicationContext(),drap, Toast.LENGTH_LONG).show();
			
				//If we want send Student's informations
			  if(drap.compareTo("Student")==0){
				Intent i=new Intent(getApplicationContext(),Details.class);
				i.putExtra("Name", AL_S.get(position).getName());
				i.putExtra("Prenom", AL_S.get(position).getPrenom());
				i.putExtra("University", AL_S.get(position).getUniversity());
				i.putExtra("Sexe", AL_S.get(position).getSexe());
				i.putExtra("telephone"," ");
				i.putExtra("email"," ");
				i.putExtra("flag","Student");
				startActivity(i);
				}
			  //If we want send Staff's informations
				if(drap.compareTo("Staff")==0){
				Intent i=new Intent(getApplicationContext(),Details.class);
				i.putExtra("Name", AL_St.get(position).getName());
				i.putExtra("Prenom", AL_St.get(position).getPrenom());
				i.putExtra("University", AL_St.get(position).getUniversity());
				i.putExtra("Sexe", AL_St.get(position).getSexe());
				i.putExtra("telephone",AL_St.get(position).getTelephone());
				i.putExtra("email",AL_St.get(position).getEmail());
				i.putExtra("flag","Staff");
				startActivity(i);
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
        getMenuInflater().inflate(R.menu.contact_list, menu);
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
    
  //------Function for request the web service and getting students 
  	public void RequestWS(){
  		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		   // Make request to Web Service call using AsyncHttpClient object
		
		
		SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String server = setting_prefs.getString("server_address", null);
		String addr = new StringBuilder().append("http://").append(server).append("/contact/students").toString();
		
		  AsyncHttpClient client = new AsyncHttpClient();
		  client.get(addr,params,new AsyncHttpResponseHandler() {
		  						              			           
		 
		
						@Override
						public void onFailure(int arg0, Header[] arg1, byte[] arg2,
								Throwable arg3) {
							Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
			
		}
		
		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] response) {
			 try {
		    	   //Create JSON Object from the String "response" receive from the server 
		    	 JSONObject obj = new JSONObject(new String(response));              	 
						try{
							//Fill our student list
		    				JSONArray JA= obj.getJSONArray("students");
		     				//Toast.makeText(getApplicationContext(), "Success JSON ARRAY", Toast.LENGTH_LONG).show();
		     				//Toast.makeText(getApplicationContext(), JA.toString() , Toast.LENGTH_LONG).show();		         			
							         for(int i=0;i< JA.length();i++)
							            {
							           	Student LocalEv=new Student();
							         	JSONObject student=JA.getJSONObject(i);
							         	JSONObject fields = student.getJSONObject("fields");
							         	//Toast.makeText(getApplicationContext(),student.toString(), Toast.LENGTH_LONG).show();
							         	LocalEv.setName(fields.getString(TAG_NOM));
							         	LocalEv.setPrenom(fields.getString(TAG_PRENOM));
							         	LocalEv.setUniversity(fields.getString(TAG_UNIVERSITY));
							         	LocalEv.setSexe(fields.getString(TAG_SEXE));										  										       									         			
							         	AL_S.add(LocalEv);  										         					
							            }
							         	//Toast.makeText(getApplicationContext(),"PARSIng TERMINE", Toast.LENGTH_LONG).show();
							         	
							       
							     } catch(Exception e){
							         				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
							     					}
							         			
							                      										               
		    	  } catch (JSONException e) {
							                     // TODO Auto-generated catch block
		    		  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				 
		    	  							}
		}
          	
  			});

  	}
  	
  //------Function for request the web service and getting students 
  	public void RequestWStaff(){
  		// TODO Auto-generated method stub
  		RequestParams params = new RequestParams();
           // Make request to Web Service call using AsyncHttpClient object
          AsyncHttpClient client = new AsyncHttpClient();
          //http://172.17.67.0 -----//----- 192.168.2.55
          SharedPreferences setting_prefs = PreferenceManager.getDefaultSharedPreferences(this);
  		String server = setting_prefs.getString("server_address", null);
  		String addr = new StringBuilder().append("http://").append(server).append("/contact/staffs").toString();
  		
          client.get(addr,params,new AsyncHttpResponseHandler() {
  						              			           
          
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
					
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] response) {
					try {
	  		        	   //Create JSON Object from the String "response" receive from the server 
	  		                	 JSONObject obj = new JSONObject(new String(response));              	 
	  									try{
	  										//Fill our student list
	  					    				JSONArray JA= obj.getJSONArray("staffs");
	  			             				//Toast.makeText(getApplicationContext(), "Success JSON ARRAY", Toast.LENGTH_LONG).show();
	  					     				//Toast.makeText(getApplicationContext(), JA.toString() , Toast.LENGTH_LONG).show();		         			
	  										         for(int i=0;i< JA.length();i++)
	  										            {
	  										           	Staff LocalEv=new Staff();
	  										         	JSONObject staff=JA.getJSONObject(i);
	  										         	JSONObject fields = staff.getJSONObject("fields");
	  										         	//Toast.makeText(getApplicationContext(),student.toString(), Toast.LENGTH_LONG).show();
	  										         	LocalEv.setName(fields.getString(TAG_NOM));
	  										         	LocalEv.setPrenom(fields.getString(TAG_PRENOM));
	  										         	LocalEv.setUniversity(fields.getString(TAG_UNIVERSITY));
	  										         	LocalEv.setSexe(fields.getString(TAG_SEXE));	
	  										         	LocalEv.setTelephone(fields.getString(TAG_TEL));
	  										         	LocalEv.setEmail(fields.getString(TAG_EMAIL));
	  										         	AL_St.add(LocalEv);  										         					
	  										            }
	  										         	//Toast.makeText(getApplicationContext(),"PARSIng TERMINE", Toast.LENGTH_LONG).show();
	  										         	
	  										       
	  										     } catch(Exception e){
	  										         				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	  										     					}
	  										         			
	  										                      										               
	  				        	  } catch (JSONException e) {
	  										                     // TODO Auto-generated catch block
	  				        		  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	  							 
	  			            	  							}
					
				}
          	
  																});

  	}


@Override
public void run() {
	// TODO Auto-generated method stub
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
