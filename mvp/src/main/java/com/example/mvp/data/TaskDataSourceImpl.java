package com.example.mvp.data;

import android.content.Context;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;



/**
 * Created by Administrator on 2016/8/10.
 */
public class TaskDataSourceImpl implements TaskDataSource ,Callback{

    String path;




    NetOnListen netOnListen;
    @Override
    public void getStringFromCache(NetOnListen netOnListen) {
        init(path);
        this.netOnListen=netOnListen;
    }

    @Override
    public String getStringFromSh() {
        return "hahahaha";
    }

    @Override
    public void getPath(String path) {
        this.path =path;
    }

    public void init(String path) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url(path)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);

        //请求加入调度
        call.enqueue(this);

    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        netOnListen.getJson(response.body().string());

    }


}
