package com.sjjd.wyl.playandroid.net;

/**
 * Created by wyl on 2018/4/27.
 */

public interface L {
    interface URL_MAIN {

        String Get_Banner = "http://www.wanandroid.com/banner/json";
        String Get_List = "http://www.wanandroid.com/article/list/%1$d/json";
        String Get_Search = "http://www.wanandroid.com/article/query/%1$d/json";
    }

    interface CODE {
        int MSG_MAIN_BANNER_SUCCESS = 2000;
        int MSG_MAIN_ARTICLE_SUCCESS = 2008;
        int MSG_DATA_FAILED = -1;


    }
}