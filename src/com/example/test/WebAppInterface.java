package com.example.test;


import org.json.JSONException;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class WebAppInterface {
	private static final String TAG = null;
	public WebView mwebView;
	@SuppressLint("NewApi") @JavascriptInterface
    public void receive(String[] newData)throws JSONException {
		
		if(newData==null)
		{
			Log.d(TAG, "NO DATA");
		}
		else
		{		
			String s = newData[0].toString().trim();
			MainActivity.s2 = s.split(":::");			
		}
}
}