package com.sjjd.wyl.playandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyl on 2018/5/4.
 */

public class UserBean implements Serializable {

    public class Data implements Serializable{
        private List<Integer> collectIds;

        private String email;

        private String icon;

        private int id;

        private String password;

        private int type;

        private String username;

        public void setCollectIds(List<Integer> collectIds) {
            this.collectIds = collectIds;
        }

        public List<Integer> getCollectIds() {
            return this.collectIds;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return this.password;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }
    }


    private Data data;

    private int errorCode;

    private String errorMsg;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
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

}
