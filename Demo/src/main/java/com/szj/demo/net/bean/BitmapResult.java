package com.szj.demo.net.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片请求结果数据结构
 * Created by Administrator on 2016/7/5.
 *
 */
public class BitmapResult {
    /**
     * 跟请求的图片绑定的那个ImageView
     */
    public ImageView netImageView;

    /**
     * 图片cover地址，相对的地址，不包含主机名
     */
    public String cover;

    /**
     * 图片对象
     */
    public Bitmap bimtap;

    /**
     * 网络请求结果码
     */
    public int resultCode;
}
