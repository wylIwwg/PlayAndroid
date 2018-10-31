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

    interface SP {
        String USER_SPNAME = "user";
        String USER_LOGINED = "user_logined";
        String USER_NAME = "username";
    }

    interface URL {

        String Get_Banner = "http://www.wanandroid.com/banner/json";
        String Get_List = "http://www.wanandroid.com/article/list/%1$d/json";
        String Get_New_ProjectList = "http://www.wanandroid.com/article/listproject/%1$d/json";
        String Get_Search = "http://www.wanandroid.com/article/query/%1$d/json";
        String Get_HotWords = "http://www.wanandroid.com//hotkey/json";


        String Post_Login = "http://www.wanandroid.com/user/login";
        String Post_Register = "http://www.wanandroid.com/user/register";
        String Get_Collect = "http://www.wanandroid.com/lg/collect/list/%1$d/json";
        String Post_Collect = "http://www.wanandroid.com/lg/collect/%1$d/json";
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
