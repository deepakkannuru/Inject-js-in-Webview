package com.example.test;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
	
	    @JavascriptInterface
	    void receiveString(String value) {
	        // String received from WebView
	        Log.d("MyApp", value);
	    }
	}
