package com.example.mvp.presenter;

import android.content.Context;

import com.example.mvp.data.NetOnListen;
import com.example.mvp.data.TaskDataSourceImpl;

import com.example.mvp.view.MainView;

import dalvik.system.DexClassLoader;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Administrator on 2016/8/10.
 */
public class MainPresenter {

    MainView mainView;

    Context context;
    String path ="http://www.tngou.net/api/lore/list";
    String path2 ="http://www.tngou.net/api/book/classify";
    TaskDataSourceImpl sourceImpl;

    // 控制层将V层 和 M层关联
    public MainPresenter(MainView viewListener){

        this.mainView = viewListener;
        sourceImpl =new TaskDataSourceImpl();

     /*   111*/
    }


    boolean isOK;
    public void addItem(){

        if (isOK){
            sourceImpl.getPath(path);
        }else {
            sourceImpl.getPath(path2);
        }
        isOK =!isOK;


    }

    // 依赖倒置
    public void getString() {
        mainView.onShowProgress();
        final String str =  sourceImpl.getStringFromSh();

        sourceImpl.getStringFromCache(new NetOnListen() {
            @Override
            public void getJson(String string) {
                toMinThread(str+string);
            }
        });
    }

    /**
     *
     * @param string 要返回主线程的信息
     */
    private void toMinThread(final String string) {

        //这只是跨线程 所以操作在返回的地方
        Func1 dataAction = new Func1<String,String>() {
            @Override
            public String call(String param){


                return string;
            };


        };
        Action1 viewAction = new Action1<String>() {
            @Override
            public void call( String str) {

                mainView.onShowString(str);
                mainView.onNoShowProgress();
            }
        };
        Observable.just("")
                .observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);

    }
}
