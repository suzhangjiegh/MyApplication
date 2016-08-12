package com.example.mvp.data;

/**
 *
 * Created by Administrator on 2016/8/10.
 *
 *
 * M层 接口
 */
public interface TaskDataSource {

    void getStringFromCache(NetOnListen netOnListen);

    String getStringFromSh();

    void  getPath(String path);
}
