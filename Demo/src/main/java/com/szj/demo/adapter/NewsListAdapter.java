package com.szj.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szj.demo.R;
import com.szj.demo.bean.NewsData;
import com.szj.demo.net.NewsApi;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 *
 * Created by Administrator on 2016/7/21.
 */
public class NewsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    ArrayList<NewsData> lists;

    public static final int FOOTER_VIEW = 101;
    public View mFootView;
    private int footViewSize;

    public NewsListAdapter() {
        if (lists == null) {
            lists = new ArrayList<>();
        }
    }

    public NewsListAdapter(ArrayList<NewsData> data) {
        lists = data == null ? new ArrayList<NewsData>() : data;

    }

    public void updata(ArrayList<NewsData> l) {
        lists = l;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == FOOTER_VIEW) {

            return new BaseViewHolder(parent.getContext(),mFootView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
            return new BaseViewHolder(parent.getContext(),view);

        }


    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder,final int position) {
        if (holder.getItemViewType()==FOOTER_VIEW)return;
        NewsData newsData =lists.get(position);
        convert(holder,newsData);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(v, position, lists.get(position));
            }
        });
    }


    @Override
    public int getItemViewType(int position) {

        if (position == lists.size() &&footViewSize==1) {
            return FOOTER_VIEW;
        } else {
            return super.getItemViewType(position);
        }

    }








    public void addFootView(View view) {
        mFootView = view;

        mFootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        footViewSize = 1;
    }


    private void convert(BaseViewHolder holder, NewsData newsData) {

        holder.setText(R.id.news_list_subject_tv, newsData.getSubject())
                .setText(R.id.news_list_summary_tv, newsData.getSummary())
                .setText(R.id.news_list_time_tv, newsData.getChanged())
                .setImgByGlide(R.id.news_list_img, NewsApi.HOST_NAME + newsData.getCover());


    }

    @Override
    public int getItemCount() {
        return lists.size() + footViewSize;
    }


    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position, NewsData data);
    }


}

