package com.szj.demo.net;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.szj.demo.net.bean.BitmapResult;
import com.szj.demo.net.bean.StringResult;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 */
public class HttpReq {


    /**
     * 网络结果返回码
     */
    public static final int RESULT_OK = 2000;


    //超时时间
    private static final int TIME_OUT = 5 * 10000000;

    /**
     * json请求接口
     */
    public interface OnJsonDataListener {
        void jsonData(StringResult stringResult);
    }

    /**
     * bitmap请求接口
     */
    public interface OnImageBitmapListener {
        void onImageBitmap(BitmapResult result);
    }


    public static HttpReq Instance;
    public static HttpReq getInstance() {
        if (Instance == null) {
            synchronized (HttpReq.class) {
                if (Instance == null) {
                    Instance = new HttpReq();

                }
            }
        }
        return Instance;
    }
    /**
     * @param path     请求地址
     * @param listener Json回调
     */
    public void getJsonData(String path, OnJsonDataListener listener) {
        new Thread(new JsonDataRunnable(path, listener)).start();
    }

    private class JsonDataRunnable implements Runnable {
        private String path;
        private OnJsonDataListener listener;

        public JsonDataRunnable(String path, OnJsonDataListener listener) {
            this.path = path;
            this.listener = listener;
        }

        @Override
        public void run() {
            StringResult stringResult;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 提交模式
                conn.setRequestMethod("GET");
                //连接超时
                conn.setConnectTimeout(TIME_OUT);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inStream = conn.getInputStream();
                    byte[] data = readInputStream(inStream);
                    String json = new String(data, "utf-8");
                    stringResult = new StringResult(HttpURLConnection.HTTP_OK, json);
                } else {
                    stringResult = new StringResult(conn.getResponseCode(), null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                stringResult = new StringResult(-1, null);
            }

            listener.jsonData(stringResult);
        }
    }


    public void postJsonData(String path, Map<String, String> params, OnJsonDataListener listener) {

        new Thread(new JsonPostDataRunnable(path, params, listener)).start();
    }

    private class JsonPostDataRunnable implements Runnable {
        private String path;
        private Map<String, String> params;
        private OnJsonDataListener listener;


        public JsonPostDataRunnable(String path, Map<String, String> params, OnJsonDataListener listener) {
            this.path = path;
            this.params = params;
            this.listener = listener;

        }

        @Override
        public void run() {
            // 作为StringBuffer初始化的字符串
            StringBuffer buffer = new StringBuffer();
            StringResult stringResult;

            try {
                if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        // 完成转码操作
                        buffer.append(entry.getKey()).append("=").append(
                                URLEncoder.encode(entry.getValue(), "utf-8"))
                                .append("&");
                    }
                    buffer.deleteCharAt(buffer.length() - 1);
                }
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("POST");
                // 表示从服务器获取数据
                conn.setDoInput(true);
                // 表示向服务器写数据
                conn.setDoOutput(true);
                // 获得上传信息的字节大小以及长度
                byte[] mydata = buffer.toString().getBytes();
                // 表示设置请求体的类型是文本类型
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String
                        .valueOf(mydata.length));
                // 获得输出流,向服务器输出数据
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(mydata, 0, mydata.length);
                outputStream.close();

                // 获得服务器响应的结果和状态码
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    InputStream inStream = conn.getInputStream();
                    byte[] data = readInputStream(inStream);
                    String json = new String(data, "utf-8");
                    stringResult = new StringResult(HttpURLConnection.HTTP_OK, json);
                } else {
                    stringResult = new StringResult(conn.getResponseCode(), null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                stringResult = new StringResult(-1, null);
            }
            listener.jsonData(stringResult);
        }
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream 输入流
     * @return 返回byte数组
     * @throws Exception
     */
    public byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }


}
