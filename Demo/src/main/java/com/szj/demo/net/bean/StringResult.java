package com.szj.demo.net.bean;

/**
 * 文字结果数据结构
 *
 * Created by Administrator on 2016/7/5.
 */
public class StringResult {
    public StringResult() {

    }

    // 网络连接的结果码
    public int resultCode;

    public String jsonResponse;

    public StringResult(int resultCode, String jsonResponse) {
        super();
        this.resultCode = resultCode;
        this.jsonResponse = jsonResponse;
    }
}
