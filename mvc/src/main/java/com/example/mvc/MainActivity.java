package com.example.mvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvc.adapter.MyAdapter;
import com.example.mvc.bean.Student;
import com.example.mvc.model.TaskDataSourceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TaskDataSourceImp.OnListenerData {

    //开始就是快速排序
    private boolean isQuick =true;
    private Button main_update;

    private Button main_sort;

    private ListView main_lv;

    private  TaskDataSourceImp taskDataSourceImp;

    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        main_update = (Button) findViewById(R.id.main_update);
        main_sort = (Button) findViewById(R.id.main_sort);
        main_lv = (ListView) findViewById(R.id.main_lv);

        main_update.setOnClickListener(this);
        main_sort.setOnClickListener(this);



        adapter =new MyAdapter();
        main_lv.setAdapter(adapter);

        taskDataSourceImp =new TaskDataSourceImp();
        taskDataSourceImp.setOnListenerData(this);

        taskDataSourceImp.getDateByUpDate();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_update:
                taskDataSourceImp.getDateByUpDate();
                Toast.makeText(this,"重新加载数据",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_sort:

                if (isQuick){
                    taskDataSourceImp.getDateBySortQuick();
                    Toast.makeText(this,"快速排序",Toast.LENGTH_SHORT).show();
                }else {
                    taskDataSourceImp.getDateBySortBubble();
                    Toast.makeText(this,"冒泡排序",Toast.LENGTH_SHORT).show();
                }
                isQuick=!isQuick;

                break;
        }
    }

    @Override
    public void onData(ArrayList<Student> list) {

        adapter.setData(list);


    }
}
