package com.sjjd.wyl.playandroid.model.utils;

/**
 * Created by wyl on 2018/4/27.
 */

public interface L {
    interface URL {

        String Get_Banner = "http://www.wanandroid.com/banner/json";
        String Get_List = "http://www.wanandroid.com/article/list/%1$d/json";
        String Get_Search = "http://www.wanandroid.com/article/query/%1$d/json";
        String Get_HotWords = "http://www.wanandroid.com//hotkey/json";


        String Post_Login = "http://www.wanandroid.com/user/login";
        String Post_Register = "http://www.wanandroid.com/user/register";

        String Get_Collect = "http://www.wanandroid.com/lg/coll/list/%1$d/json";
        String Post_Collect = "http://www.wanandroid.com/lg/coll/%1$d/json";
        String Post_UNCollect_ArticleList = "http://www.wanandroid.com/lg/uncollect_originId/%1$d/json";
        String Post_UNCollect_MyCollect = "http://www.wanandroid.com/lg/uncollect/%1$d/json";
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

    interface BROADCAST {
        String KEY = "KEY_CHANGED";
    }
}