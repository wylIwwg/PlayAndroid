package com.sjjd.wyl.playandroid.bean;

import java.util.List;

/**
 * Created by wyl on 2018/5/3.
 */

public class ProjectBean {

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

    public class Datas {
        private String apkLink;

        private String author;

        private int chapterId;

        private String chapterName;

        private boolean collect;

        private int courseId;

        private String desc;

        private String envelopePic;

        private boolean fresh;

        private int id;

        private String link;

        private String niceDate;

        private String origin;

        private String projectLink;

        private long publishTime;

        private long superChapterId;

        private String superChapterName;

        private List<String> tags;

        private String title;

        private int type;

        private int visible;

        private int zan;

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getApkLink() {
            return this.apkLink;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return this.author;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public int getChapterId() {
            return this.chapterId;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getChapterName() {
            return this.chapterName;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public boolean getCollect() {
            return this.collect;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getCourseId() {
            return this.courseId;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public String getEnvelopePic() {
            return this.envelopePic;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public boolean getFresh() {
            return this.fresh;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceDate() {
            return this.niceDate;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getOrigin() {
            return this.origin;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public String getProjectLink() {
            return this.projectLink;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public long getPublishTime() {
            return this.publishTime;
        }

        public void setSuperChapterId(long superChapterId) {
            this.superChapterId = superChapterId;
        }

        public long getSuperChapterId() {
            return this.superChapterId;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getSuperChapterName() {
            return this.superChapterName;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getTags() {
            return this.tags;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getVisible() {
            return this.visible;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public int getZan() {
            return this.zan;
        }
    }


    public class Data {
        private int curPage;

        private List<Datas> datas;

        private int offset;

        private boolean over;

        private int pageCount;

        private int size;

        private int total;

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getCurPage() {
            return this.curPage;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public List<Datas> getDatas() {
            return this.datas;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public boolean getOver() {
            return this.over;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageCount() {
            return this.pageCount;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return this.total;
        }
    }
}
