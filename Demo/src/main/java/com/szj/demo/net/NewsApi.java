package com.szj.demo.net;

/**
 * Created by Administrator on 2016/7/20.
 */

public class NewsApi {
    /**
     * 头条类别新闻
     */
    public static final int TOP_LINE = 0;
    /**
     * 社会类别新闻
     */
    public static final int SOCIETY = 1;
    /**
     * 真相类别新闻
     */
    public static final int TRUTH = 2;
    /**
     * 国内类别新闻
     */
    public static final int INLAND = 3;
    /**
     * 国际类别新闻
     */
    public static final int INTERNATIONAL = 4;
    /**
     * 军事类别新闻
     */
    public static final int MILITARY = 5;
    /**
     * 体育类别新闻
     */
    public static final int SPORTS = 6;
    /**
     * 娱乐类别新闻
     */
    public static final int RECREATION = 7;
    /**
     * 服务器域名
     */
    public static final String HOST_NAME = "http://litchiapi.jstv.com";




    /**
     * @param type 类别
     * @param pageNum   页码
     * @param pageCount 每页容量
     * @return 完整接口
     */
    public static String getListAdd(int type, int pageNum, int pageCount) {
        return  HOST_NAME + "/api/GetFeeds?column="+type+"&PageSize="+pageCount+"&pageIndex="+pageNum+"&val=100511D3BE5301280E0992C73A9DEC41";

    }

    public static final String DETAIL_ADDRESS = "http://litchi.jstv.com/Wap/Article/";


}
