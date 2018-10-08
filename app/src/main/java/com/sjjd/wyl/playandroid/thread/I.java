package com.sjjd.wyl.playandroid.thread;

/**
 * Created by wyl on 2018/10/8.
 */
public interface I {
    int LOAD_DATA_SUCCESS = 200;//数据加载成功
    int LOAD_DATA_FAILD = -1;//数据加载失败
    int NET_ERROR = 300;//网络错误
    int SERVER_ERROR = 400;//服务器错误
    int UNKNOWN_ERROR = 250;//未知错误
    int TIMEOUT = 201;//请求超时

    interface  SP{
        String USER_SPNAME="user";
        String USER_LOGINED="user_logined";
        String USER_NAME="username";
    }
}
