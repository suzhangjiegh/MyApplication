package com.example.mvc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mvc.bean.Student;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/19.
 */
public class MyAdapter  extends BaseAdapter{

    private ArrayList<Student> list;

    public MyAdapter(){
        list =new ArrayList<>();
    }

    public void  setData(ArrayList<Student> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Student getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student =list.get(position);
        if (convertView ==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            ViewHolder holder =new ViewHolder();
            holder.textView= (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textView.setText(student.getName()+"\r\t\t成绩："+student.getGrade());
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
