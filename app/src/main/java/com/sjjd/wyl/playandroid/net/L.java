package com.sjjd.wyl.playandroid.net;

/**
 * Created by wyl on 2018/4/27.
 */

public interface L {
    interface URL_MAIN {

        String Get_Banner = "http://www.wanandroid.com/banner/json";
        String Get_List = "http://www.wanandroid.com/article/list/%1$d/json";
        String Get_Search = "http://www.wanandroid.com/article/query/%1$d/json";
        String Get_HotWords = "http://www.wanandroid.com//hotkey/json";
    }


    interface CATEGORY {
        String Get_Category = "http://www.wanandroid.com/tree/json";
        String Get_Category_ArticleList = "http://www.wanandroid.com/article/list/%1$d/json?cid=%2$d";
    }

    interface CODE {
        int MSG_MAIN_BANNER_SUCCESS = 1000;
        int MSG_MAIN_HOTWORDS_SUCCESS = 1011;
        int MSG_MAIN_ARTICLE_SUCCESS = 1008;
        int MSG_DATA_FAILED = -1;


        int MSG_CATEGORY_SUCCESS = 20001;
        int MSG_CATEGORY_ARTICLES_SUCCESS = 20002;
    }
}