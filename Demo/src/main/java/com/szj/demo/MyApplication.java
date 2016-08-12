package com.szj.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;
import com.szj.demo.utils.MyUtil;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    public BMapManager mBMapManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //在使用百度SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        initEngineManager(this);
    }

    //初始化地图信息
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context.getApplicationContext());
        }
        if (!mBMapManager.init(new MyGeneralListener())) {
            MyUtil.showToast(context.getApplicationContext(), "初始化错误");
        }
        Log.d(TAG, "initEngineManager");
    }


    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                MyUtil.showToast(getApplicationContext(), "地图授权错误");
            }
        }
    }
}
