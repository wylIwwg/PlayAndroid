package com.sjjd.wyl.playandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyl on 2018/4/27.
 */

public class BannerBean implements Serializable {


    private List<Data> data;
    private int errorcode;
    private String errormsg;

    @Override
    public String toString() {
        return "BannerBean{" +
                "data=" + data +
                ", errorcode=" + errorcode +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }


    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public class Data implements Serializable{

        private String desc;
        private int id;
        private String imagePath;
        private int isVisible;
        private int order;
        private String title;
        private int type;
        private String url;


        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }


        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }


        public void setImagepath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getImagepath() {
            return imagePath;
        }


        public void setIsvisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public int getIsvisible() {
            return isVisible;
        }


        public void setOrder(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }


        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }


        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }


        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }


        @Override
        public String toString() {
            return "Data{" +
                    "desc='" + desc + '\'' +
                    ", id=" + id +
                    ", imagepath='" + imagePath + '\'' +
                    ", isvisible=" + isVisible +
                    ", order=" + order +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}



