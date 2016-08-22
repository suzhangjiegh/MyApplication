package com.example.mvc.model;

import android.util.Log;

import com.example.mvc.bean.Student;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/19.
 */
public class TaskDataSourceImp implements TaskDataSource {

    private ArrayList<Student> list;

    //学生数量
    private static final int STUDENT_NUM = 20;
    //最大成绩
    private static final int GRADE_MAX = 100;

    private OnListenerData listen;


    public void setOnListenerData(OnListenerData l) {
        listen = l;
    }

    @Override
    public void getDateByUpDate() {
        list = new ArrayList<>();
        Random random = new Random();
        boolean[] bool = new boolean[STUDENT_NUM + 1];
        int num = 0;
        for (int i = 0; i < STUDENT_NUM; i++) {
            num = random.nextInt(STUDENT_NUM) + 1;
            if (bool[num]) {
                i--;
                continue;
            }
            bool[num] = true;
            list.add(new Student("学生" + num, random.nextInt(GRADE_MAX)));
        }
        listen.onData(list);
    }


    @Override
    public void getDateBySortBubble() {
        for (int i = 0; i < list.size(); i++) {
            Student s;
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getGrade() > list.get(j + 1).getGrade()) {
                    s = list.get(j);
                    list.set((j), list.get(j + 1));
                    list.set(j + 1, s);
                }
            }
        }
        listen.onData(list);
    }

    @Override
    public void getDateBySortQuick() {
        sort(list, 0, list.size() - 1);
        listen.onData(list);
    }

    public void sort(ArrayList<Student> l, int low, int hight) {
        if (low < hight) {
            int result = partition(l, low, hight);
            sort(l, low, result - 1);
            sort(l, result + 1, hight);
        }
    }

    private int partition(ArrayList<Student> l, int low, int hight) {
        int key = l.get(hight).getGrade();
        int i = low - 1;
        Student temp;
        for (int j = low; j <= hight - 1; j++) {
            if (list.get(j).getGrade() <= key) {
                i++;
                temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        temp = list.get(i + 1);
        list.set(i + 1, list.get(hight));
        list.set(hight, temp);
        return i + 1;
    }


    public interface OnListenerData {
        void onData(ArrayList<Student> list);
    }
}
