package com.szj.demo.frag;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.szj.demo.DetailActivity;
import com.szj.demo.R;
import com.szj.demo.adapter.NewsListAdapter;
import com.szj.demo.bean.NewsData;
import com.szj.demo.net.HttpReq;
import com.szj.demo.net.HttpRequest;
import com.szj.demo.net.NewsApi;
import com.szj.demo.net.bean.StringResult;
import com.szj.demo.utils.MyUtil;
import com.szj.demo.widget.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;


public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, NewsListAdapter.OnRecyclerViewItemClickListener {
    private static final String TAG = NewsListFragment.class.getSimpleName();

    private int type;
    private int PAGE_NUM = 1;
    private int PAGE_COUNT = 10;
    private String path;
    private RecyclerView mRecyclerView;
    private NewsListAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    public NewsListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.frag_news_swipe);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frag_news_list_rv);

        refreshLayout.setOnRefreshListener(this);

        Resources resources =Resources.getSystem();

        refreshLayout.setColorSchemeColors(resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_blue_dark),
                resources.getColor(android.R.color.holo_red_dark),
                resources.getColor(android.R.color.holo_orange_light));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration());


        adapter = new NewsListAdapter();

        View footView =LayoutInflater.from(this.getContext()).inflate(R.layout.item_foot, null);
        adapter.addFootView(footView);
        mRecyclerView.setAdapter(adapter);

        footView.setOnClickListener(this);
        adapter.setOnRecyclerViewItemClickListener(this);

        path = NewsApi.getListAdd(type, PAGE_NUM, PAGE_COUNT);
        initNetWork(path);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"type ="+type);

    }

    private void initNetWork(String path) {
        Response.Listener<String> list = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<NewsData> datas = null;
                try {
                    datas = parseToJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.updata(datas);
                refreshLayout.setRefreshing(false);

            }
        };
        Response.ErrorListener err = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        StringRequest requset = new StringRequest(Request.Method.GET, path, list, err);
        HttpRequest.getInstance(this.getActivity()).sendRequest(requset);

    }


    private ArrayList<NewsData> parseToJson(String stringResult) throws JSONException {
        ArrayList<NewsData> datas = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(stringResult);
        String status = jsonObject.getString("status");
        if (!status.equals("ok")) {
            return datas;
        }
        JSONObject paramz = jsonObject.getJSONObject("paramz");
        JSONArray feeds = paramz.getJSONArray("feeds");
        for (int i = 0; i < feeds.length(); i++) {
            JSONObject newsJson = feeds.getJSONObject(i);
            NewsData data = NewsData.initWithJsonObject(newsJson);
            datas.add(data);
        }

        return datas;
    }


    @Override
    public void onRefresh() {
        PAGE_NUM=1;
        path = NewsApi.getListAdd(type, PAGE_NUM, PAGE_COUNT);
        initNetWork(path);
    }

    @Override
    public void onClick(View v) {
        PAGE_COUNT=PAGE_COUNT+10;
        path = NewsApi.getListAdd(type, PAGE_NUM, PAGE_COUNT);
        initNetWork(path);
    }

    @Override
    public void onItemClick(View view, int position, NewsData data) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("oid", data.oid);
        startActivity(intent);
    }
}
