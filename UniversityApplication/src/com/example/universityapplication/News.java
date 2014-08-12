package com.example.universityapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

// Third Activity = NEWS
public class News extends Activity 
{

	private WebView wv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		// Identify the buttons and the web viewer by resource ID
		ImageButton btnSummerschool = (ImageButton) findViewById(R.id.imageButtonHome);
		ImageButton btnAal = (ImageButton) findViewById(R.id.imageButtonAal);
		ImageButton btnAau = (ImageButton) findViewById(R.id.imageButtonAau);
		ImageButton btnWeather = (ImageButton) findViewById(R.id.imageButtonWeather);
		wv = (WebView) findViewById(R.id.webViewNews);
		
		// Enable JavaScript
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// Load the default URL in the web viewer
		wv.loadUrl("http://www.summerschool.aau.dk/");
		
		// Set the listener for the first button : Aalborg University Summer school
		btnSummerschool.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// Load the URL in the web viewer 
				wv.loadUrl("http://www.summerschool.aau.dk/");
				
			}
		});
		
		// Set the listener for the first button : Aalborg News
		btnAal.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// Load the URL in the web viewer 
				wv.loadUrl("https://news.search.yahoo.com/search;_ylt=AwrBEiIUd99TqDwAfv3QtDMD;_ylc=X1MDNTM3MjAyNzIEX3IDMgRiY2sDMXY4bmh0dDlza2p1NyUyNmIlM0QzJTI2cyUzRDYxBGZyA3VoM19uZXdzX3dlYl9ncwRncHJpZANzX0tIUFZyVVRsdUFpSERpRXpQbEFBBG10ZXN0aWQDbnVsbARuX3JzbHQDMTAEbl9zdWdnAzIEb3JpZ2luA25ld3Muc2VhcmNoLnlhaG9vLmNvbQRwb3MDMARwcXN0cgMEcHFzdHJsAwRxc3RybAM3BHF1ZXJ5A0FhbGJvcmcEdF9zdG1wAzE0MDcxNTQwMjYwNjQEdnRlc3RpZANudWxs?gprid=s_KHPVrUTluAiHDiEzPlAA&pvid=fAcNRTk4LjEfovHvU8pPxwk7MTMwLlPfdxT_wuo4&p=Aalborg&fr2=sb-top-news.search.yahoo.com&fr=uh3_news_web_gs");
				
			}
		});
		
		// Set the listener for the first button : Aalborg University News
				btnAau.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						// Load the URL in the web viewer 
						wv.loadUrl("https://news.search.yahoo.com/search;_ylt=AwrBJR_Idt9TZnAAhFbQtDMD;_ylc=X1MDNTM3MjAyNzIEX3IDMgRiY2sDMXY4bmh0dDlza2p1NyUyNmIlM0QzJTI2cyUzRDYxBGZyA3VoM19uZXdzX3dlYl9ncwRncHJpZAMEbXRlc3RpZANudWxsBG5fcnNsdAMxMARuX3N1Z2cDMARvcmlnaW4DbmV3cy5zZWFyY2gueWFob28uY29tBHBvcwMwBHBxc3RyAwRwcXN0cmwDBHFzdHJsAzIwBHF1ZXJ5AyJBYWxib3JnIHVuaXZlcnNpdHkiBHRfc3RtcAMxNDA3MTUzODg0NjMyBHZ0ZXN0aWQDbnVsbA--?pvid=RKQSITk4LjEfovHvU8pPxwOsMTMwLlPfdsj_x0h4&p=%22Aalborg+university%22&fr2=sb-top-news.search.yahoo.com&fr=uh3_news_web_gs");
						
					}
				});
		
		// Set the listener for the first button : Aalborg News
		btnWeather.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// Load the URL in the web viewer 
				wv.loadUrl("https://weather.yahoo.com/denmark/nordjylland/aalborg-551890/");
				
			}
		});
}

	@Override
	// Overwrite the back button 
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		// If the back button is used
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			// If the web viewer can go back
			if (wv.canGoBack())
			{
				// Go back and display the previous page
				wv.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
