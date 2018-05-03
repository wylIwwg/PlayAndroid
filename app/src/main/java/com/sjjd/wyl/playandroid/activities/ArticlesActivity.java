package com.sjjd.wyl.playandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.net.L;
import com.sjjd.wyl.playandroid.thread.ArticleListThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticlesActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.rlvArticles)
    RecyclerView mRlvArticles;
    @BindView(R.id.imgBack)
    ImageView mImgBack;
    @BindView(R.id.srlCategory)
    SmartRefreshLayout mSrlCategory;


    NetHander mNetHander;
    LinearLayoutManager mLayoutManager;
    List<ArticleBean.Datas> mArticleList;//文章集合
    ArticleAdapter mArticleAdapter;
    ArticleListThread mArticleListThread;
    Context mContext;
    int cid;
    String title = "";
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        mContext = this;
        cid = getIntent().getIntExtra("cid", -1);
        title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
        setListener();
        initData();
    }

    private void setListener() {
        mSrlCategory.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mArticleListThread = new ArticleListThread(mContext, mNetHander, page, cid);
                mArticleListThread.start();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mArticleListThread = new ArticleListThread(mContext, mNetHander, page, cid);
                mArticleListThread.start();
            }
        });
    }

    private void initData() {
        mArticleList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        mRlvArticles.setAdapter(mArticleAdapter);
        mRlvArticles.setLayoutManager(mLayoutManager);
        mNetHander = new NetHander(this);
        mArticleListThread = new ArticleListThread(mContext, mNetHander, page, cid);
        mArticleListThread.start();

    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        this.finish();
    }

    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case L.CODE.MSG_CATEGORY_ARTICLES_SUCCESS:
                    ArticleBean article = (ArticleBean) msg.obj;
                    initArticle(article);
                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }

            mSrlCategory.finishLoadMore();
            mSrlCategory.finishRefresh();
        }

    }

    private void initArticle(ArticleBean article) {
        List<ArticleBean.Datas> mDatas = article.getData().getDatas();
        if (mDatas != null && mDatas.size() > 0)
            mArticleAdapter.refreshData(mDatas);

    }
}
