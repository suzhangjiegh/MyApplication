package com.szj.demo.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class HttpRequest {

    private static  HttpRequest Instance;
    private RequestQueue mQueue;

    public HttpRequest(Context context) {
        mQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static HttpRequest getInstance(Context c) {

        if (Instance == null) {
            synchronized (HttpRequest.class) {
                if (Instance == null) {
                    Instance =new HttpRequest(c.getApplicationContext());
                }
            }
        }
        return Instance;
    }

    public void sendRequest(Request request) {

        mQueue.add(request);
    }


}

