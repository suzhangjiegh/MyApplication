package com.szj.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.szj.demo.net.NewsApi;

public class DetailActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int oid = intent.getIntExtra("oid", -1);
        String aa =NewsApi.DETAIL_ADDRESS + oid;
        Log.i("TAG",aa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(NewsApi.DETAIL_ADDRESS + oid);
        // webView.getSettings().setJavaScriptEnabled(true);//支持js
        // webView.addJavascriptInterface(true);



    }

    @JavascriptInterface
    public void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
