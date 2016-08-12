package com.szj.demo.bean;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/7/20.
 */
public class NewsData {
    /**
     * 新闻的编号
     */
    public int id;
    /**
     * 新闻的编号
     */
    public int oid;
    /**
     * 新闻标题
     */
    public String subject;
    /**
     * 新闻内容摘要
     */
    public String summary;
    /**
     * 新闻图片地址
     */
    public String cover;
    /**
     * 新闻发布时间
     */
    public String changed;

    public NewsData(int id, int oid, String subject, String summary, String cover, String changed) {
        super();
        this.id = id;
        this.oid = oid;
        this.subject = subject;
        this.summary = summary;
        this.cover = cover;
        this.changed = changed;
    }

    public static NewsData initWithJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        NewsData newsData = null;
        int id = jsonObject.getInt("id");
        int oid = jsonObject.getInt("oid");
        JSONObject data = jsonObject.getJSONObject("data");
        String subject = data.getString("subject");
        String summary=null;
        try{
           summary = data.getString("summary");
        }catch (JSONException e){
            Log.i("TAG",e.toString());
             summary = data.getString("truth");
        }

        String cover = data.getString("cover");
        String changed = data.getString("changed");
        newsData = new NewsData(id, oid, subject, summary, cover, changed);
        return newsData;

    }


    @Override
    public String toString() {
        return "NewsData [id=" + id + ", oid=" + oid + ", subject=" + subject + ", summary="
                + summary + ", cover=" + cover + ", changed=" + changed + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }
}
