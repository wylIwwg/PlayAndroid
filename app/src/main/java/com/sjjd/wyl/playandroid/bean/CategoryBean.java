package com.sjjd.wyl.playandroid.bean;

import java.util.List;

/**
 * Created by wyl on 2018/5/2.
 */

public class CategoryBean {
    private List<Data> data;

    private int errorCode;

    private String errorMsg;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public static class Children {
        private List<String> children;

        private int courseId;

        private int id;

        private String name;

        private int order;

        private int parentChapterId;

        private int visible;

        public void setChildren(List<String> children) {
            this.children = children;
        }

        public List<String> getChildren() {
            return this.children;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getCourseId() {
            return this.courseId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getParentChapterId() {
            return this.parentChapterId;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getVisible() {
            return this.visible;
        }
    }


    public class Data {
        private List<Children> children;

        private int courseId;

        private int id;

        private String name;

        private int order;

        private int parentChapterId;

        private int visible;

        public void setChildren(List<Children> children) {
            this.children = children;
        }

        public List<Children> getChildren() {
            return this.children;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getCourseId() {
            return this.courseId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getParentChapterId() {
            return this.parentChapterId;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getVisible() {
            return this.visible;
        }
    }


}
