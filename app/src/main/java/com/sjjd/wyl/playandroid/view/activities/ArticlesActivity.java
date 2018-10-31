package com.sjjd.wyl.playandroid.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.library.flowlayout.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.base.BaseActivity;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.presenter.PrestenerArticle;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticlesActivity extends BaseActivity<ArticleBean> implements IMainView<ArticleBean> {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.rlvArticles)
    RecyclerView mRlvArticles;
    @BindView(R.id.imgBack)
    ImageView mImgBack;
    @BindView(R.id.srlCategory)
    SmartRefreshLayout mSrlCategory;


    LinearLayoutManager mLayoutManager;
    List<ArticleBean.Datas> mArticleList;//文章集合
    ArticleAdapter mArticleAdapter;
    Context mContext;
    int cid;
    String title = "";
    int page = 0;
    boolean isMore = false;

    PrestenerArticle<ArticleBean> mPrestener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        mContext = this;
        mPrestener = new PrestenerArticle<>(this);
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
                mPrestener.getArticle(mContext, I.CATEGORY.Get_Category_ArticleList, page, cid, null);
                isMore = true;
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPrestener.getArticle(mContext, I.CATEGORY.Get_Category_ArticleList, page, cid, null);

                isMore = false;
            }
        });
    }

    private void initData() {
        mArticleList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        mRlvArticles.setAdapter(mArticleAdapter);
        mRlvArticles.setLayoutManager(mLayoutManager);
        mRlvArticles.addItemDecoration(new SpaceItemDecoration(5));
        mPrestener.getArticle(mContext, I.CATEGORY.Get_Category_ArticleList, page, cid, null);


    }

    @Override
    public void onError(String error) {
        super.onError(error);
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(ArticleBean result) {
        super.onSuccess(result);
        initArticle(result);
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        this.finish();
    }


    private void initArticle(ArticleBean article) {
        List<ArticleBean.Datas> mDatas = article.getData().getDatas();
        if (mDatas != null && mDatas.size() > 0)
        {
            mArticleAdapter.refreshData(mDatas, isMore);
        }else {
            Toast.makeText(mContext, "没有更多数据啦！", Toast.LENGTH_SHORT).show();
        }

        mSrlCategory.finishLoadMore();
        mSrlCategory.finishRefresh();
        isMore = false;

    }
}
