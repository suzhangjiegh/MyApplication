package com.example.mvc.model;

import com.example.mvc.bean.Student;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface TaskDataSource {
    void getDateByUpDate();
    void getDateBySortQuick();
    void getDateBySortBubble();
}
