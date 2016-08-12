package com.szj.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.szj.demo.R;

/**
 * Created by Administrator on 2016/7/21.
 */
public class BaseViewHolder  extends RecyclerView.ViewHolder{
    private final SparseArray<View> views;
    private final Context context;
    private View convertView;

    protected BaseViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.views = new SparseArray<View>();
        convertView = view;
    }
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView(){
        return convertView;
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }



    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public BaseViewHolder linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseViewHolder setOnClickListen(int viewId,View.OnClickListener listener){
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);

        return this;
    }


    public BaseViewHolder setImgByGlide(int viewId, CharSequence value) {
        ImageView view = retrieveView(viewId);

        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(R.mipmap.ic_launcher);
        Glide.with(context)
                .load(value)
                .thumbnail(thumbnailRequest)
                .into(view);
        return this;
    }
}
