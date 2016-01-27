package com.example.test;

import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

@SuppressLint("NewApi") public class MainActivity extends Activity {	
	public WebView mwebView;
	public Button button , button1;
	final Handler handler = new Handler();
	public static String[] s2;
//	public ProgressDialog pDialog;
	public String s1 , s3;
	public static ArrayList<SlideArray> slide = new ArrayList<SlideArray>();
	
	@SuppressLint( "SetJavaScriptEnabled") 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		mwebView  = (WebView) findViewById(R.id.webview);
//		pDialog = new ProgressDialog(getApplicationContext());
		 WebSettings webSettings = mwebView.getSettings();
	 
		 webSettings.setJavaScriptEnabled(true); //enable javascript
	 
		    mwebView.setWebViewClient(new WebViewClient() {	
		    	 @Override
		            public void onPageFinished(WebView view, String url) {		              
		              super.onPageFinished(view, url);	
		              injectJS();
		              mwebView.loadUrl("javascript:showit('Js Injected')");

		            	mwebView.loadUrl("javascript:init()");
		            }            
		        });
		       mwebView.setWebChromeClient(new WebChromeClient());	  
		       mwebView.addJavascriptInterface(new WebAppInterface(),"reciever");		       
		       mwebView.loadUrl("http://en.wikipedia.org/wiki/Anushka_Shetty"); 
		       
		       button = (Button) findViewById(R.id.My_btn1);
		       button1 = (Button) findViewById(R.id.My_btn2);
		       button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mwebView.evaluateJavascript("javascript:reciever.receive(AllKeys());" ,null); 
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ImagesTask im = new ImagesTask();
					im.execute();
					
				}
			});
		        button.setOnClickListener(new OnClickListener() {

					@Override
		            public void onClick(View v ) {	
						
						 Intent intent = new Intent(MainActivity.this, SlideView.class);	            	 
		            	  startActivity(intent);
		            }
      });	
 }
	private void injectJS() {
	    try {
	        InputStream inputStream = getAssets().open("my.js");
	        byte[] buffer = new byte[inputStream.available()];
	        inputStream.read(buffer);
	        inputStream.close();
	        String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
	        mwebView.loadUrl("javascript:(function() {" +
	                "var parent = document.getElementsByTagName('head').item(0);" +
	                "var script = document.createElement('script');" +
	                "script.type = 'text/javascript';" +
	                "script.innerHTML = window.atob('" + encoded + "');" +
	                "parent.appendChild(script)" +
	                "})()");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
}
	private class ImagesTask extends AsyncTask<Void, Void, Void> {
		@Override
	    protected void onPreExecute() {
		       
//		    	pDialog.setTitle("loading..");
//		    	pDialog.show();
//		    
		    }
		
	    @Override
	    protected Void doInBackground(Void... params) {        
	    	
			if(s2 != null) {

    			
        		int j = s2.length;	
        		for(int i = 0; i<j ; i++) {
        			SlideArray sa = new SlideArray();
        			s1 = s2[i].toString().trim();
        			if(s1.startsWith("https://")){
        				sa.imgurl = s1; 
        			}     			
        			else
        			{ 
        				sa.text = s1;
        			}
        			slide.add(sa);
        		}
        	} 
	        return null;
	    }
	    @Override
	    protected void onPostExecute(Void param) {	       
//	    	pDialog.hide();   
	    	
	    }
	}
}