package com.szj.demo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.szj.demo.frag.NewsListFragment;
import com.szj.demo.net.NewsApi;

/**
 *
 * Created by Administrator on 2016/6/30.
 */
public class NewsAdapter extends FragmentPagerAdapter{

    private final String[] TITLES = {"头条", "社会", "真相", "国内", "国际", "军事", "体育", "娱乐" };


    public NewsAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        NewsListFragment newsListFragment =new NewsListFragment();
        Bundle bundle = new Bundle();
        int type;
        switch (position){
            case 0:
                type = NewsApi.TOP_LINE;
                break;
            case 1:
                type = NewsApi.SOCIETY;
                break;
            case 2:
                type = NewsApi.TRUTH;
                break;
            case 3:
                type = NewsApi.INLAND;
                break;
            case 4:
                type = NewsApi.INTERNATIONAL;
                break;
            case 5:
                type = NewsApi.MILITARY;
                break;
            case 6:
                type = NewsApi.SPORTS;
                break;
            case 7:
                type = NewsApi.RECREATION;
                break;
            default:
                type = NewsApi.TOP_LINE;
                break;

        }
        bundle.putInt("type", type);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
