package com.example.test;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SlideView extends Activity {
	public String tag;
//	public ProgressDialog pDialog;
	String set1 ; 
	String set2 ,s4 ;
	public static Bitmap bitmap;
	ViewFlipper v1;
	private float lastX;
	int count ;
	LinearLayout lv;
	public static ArrayList<Bitmap> bmp = new ArrayList<Bitmap>();
	public static ArrayList<SlideArray1> slide1 = new ArrayList<SlideArray1>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.viewcontent); 
//    pDialog = new ProgressDialog(getApplicationContext());
        
        v1=(ViewFlipper)findViewById(R.id.imageview);
       // v1.setFlipInterval(1000);
        lv = (LinearLayout) findViewById(R.id.lin1);
        
		count = MainActivity.slide.size();
		if(count!=0)
		{
			ImagesTask img = new ImagesTask();
			img.execute();
		}     
	}		
	private class ImagesTask extends AsyncTask<Void, Void, Void> {
		TextView tv;
		@Override		
	    protected void onPreExecute() {		       
//		    	pDialog.setTitle("loading..");
//		    	pDialog.show();
//		    
		    }		
	    @Override
	    protected Void doInBackground(Void... params) {  
	    	    	
	    	for(int i = 0; i < count; i++)
	    	{	 
	    		set1 = MainActivity.slide.get(i).imgurl;
	    		set2 = MainActivity.slide.get(i).text;
	    		getBitmapFromURL(set1);	
	    		savedata(set2);
	    		}  		
	        return null;
	    }
	    
		@Override
	    protected void onPostExecute(Void param) {	       
//	    	pDialog.hide();       	
	    	for(int i = 0; i < bmp.size(); i++)
	    	{
	    		setFlipperImage2(bmp.get(i), v1);
	    	}
	    	//v1.startFlipping();
	    	
	    	for(int i=0 ; i< slide1.size() ; i++){
	    		s4 = slide1.get(i).text1;
	    		tv = new TextView(getApplicationContext());
			    tv.setText(s4);
			    tv.setTextColor(Color.WHITE);
			    lv.addView(tv);
	    	}
	    }
	}
	public static void savedata(String set2) {
		// TODO Auto-generated method stub
		SlideArray1 sb = new SlideArray1();
		sb.text1 = set2;
		slide1.add(sb);
	}
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        bitmap = BitmapFactory.decodeStream(input);
	        if(bitmap!=null)
	    	{  
	    		bmp.add(bitmap);
	    	}
	        return bitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	   private void setFlipperImage2(Bitmap bmp , ViewFlipper v) {      
	      ImageView image = new ImageView(getApplicationContext());
	      image.setScaleType(ScaleType.CENTER_CROP);
	        image.setImageBitmap(bmp);
	        v.addView(image);
	      }
	   public boolean onTouchEvent(MotionEvent touchevent) {
		switch (touchevent.getAction()) {
	    
	    case MotionEvent.ACTION_DOWN: 
	    	lastX = touchevent.getX();
	        break;
	    case MotionEvent.ACTION_UP: 
	        float currentX = touchevent.getX();
	        
	        // Handling left to right screen swap.
	        if (lastX < currentX) {
	        	
	        	// If there aren't any other children, just break.
	            if (v1.getDisplayedChild() == 0)
	            	break;
	            
	            // Next screen comes in from left.
	            v1.setInAnimation(this, R.anim.slide_in_from_left);
	            // Current screen goes out from right. 
	            v1.setOutAnimation(this, R.anim.slide_out_to_right);
	            
	            // Display next screen.
	            v1.showNext();
	         }
	                                 
	        // Handling right to left screen swap.
	         if (lastX > currentX) {
	        	 
	        	 // If there is a child (to the left), kust break.
	        	 if (v1.getDisplayedChild() == 1)
	        		 break;
	
	        	 // Next screen comes in from right.
	        	 v1.setInAnimation(this, R.anim.slide_in_from_right);
	        	// Current screen goes out from left. 
	        	 v1.setOutAnimation(this, R.anim.slide_out_to_left);
	             
	        	// Display previous screen.
	        	 v1.showPrevious();
	         }
         break;
	 }
     return false;
}
}