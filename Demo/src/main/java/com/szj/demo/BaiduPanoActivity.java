package com.szj.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.mapapi.model.LatLng;

public class BaiduPanoActivity extends AppCompatActivity implements PanoramaViewListener {

    private static final String TAG = BaiduPanoActivity.class.getSimpleName();


    private PanoramaView mPanoView;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_pano);
        latLng = getIntent().getParcelableExtra("latLng");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPanoView = (PanoramaView) findViewById(R.id.panorama);
        //设置画面清晰度
        mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
        //将坐标放上地图
        mPanoView.setPanorama(latLng.longitude, latLng.latitude);
        mPanoView.setPanoramaViewListener(this);


    }

    @Override
    public void onDescriptionLoadEnd(String s) {

    }
    /**
     * 街景改变开始触发
     */
    @Override
    public void onLoadPanoramaBegin() {

    }
    /**
     * 街景改变结束触发 可以在这里获取全景的描述信息
     */
    @Override
    public void onLoadPanoramaEnd(String s) {
        // 缩放基级别
        mPanoView.setPanoramaZoomLevel(3);
        // 显示箭头
        mPanoView.setShowTopoLink(true);
        // 偏航角
        // mPanoView.setPanoramaHeading(270);
        // 俯仰角
        mPanoView.setPanoramaPitch(0);
    }
    /**
     * 街景load发生错误触发
     */
    @Override
    public void onLoadPanoramaError(String s) {

    }

    @Override
    public void onMessage(String s, int i) {
        //渲染引擎
        //提供协助
    }

    @Override
    public void onCustomMarkerClick(String s) {

    }
}
