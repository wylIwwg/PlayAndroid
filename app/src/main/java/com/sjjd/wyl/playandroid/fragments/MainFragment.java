package com.sjjd.wyl.playandroid.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.activities.WebActivity;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.adapter.BannerPager;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.bean.BannerBean;
import com.sjjd.wyl.playandroid.net.L;
import com.sjjd.wyl.playandroid.thread.ArticleListThread;
import com.sjjd.wyl.playandroid.thread.BannerThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @BindView(R.id.etSearch)
    EditText mEtSearch;
    @BindView(R.id.btnSearch)
    Button mBtnSearch;
    @BindView(R.id.vpBanner)
    BannerPager mVpBanner;
    @BindView(R.id.rlvMain)
    RecyclerView mRlvMain;
    Unbinder unbinder;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;
    @BindView(R.id.tvTips)
    TextView mTvTips;

    BannerThread mBannerThread;
    ArticleListThread mArticleListThread;
    ArticleListThread mArticleSearchThread;
    NetHander mNetHander;
    ArticleAdapter mArticleAdapter;
    LinearLayoutManager mLayoutManager;
    List<ArticleBean.Datas> mArticleList;
    Context mContext;

    int pageCount = 0;
    String key = "";//搜索关键词

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, view);
        mSrlRoot.setRefreshHeader(new StoreHouseHeader(mContext).initWithString("PLAY ANDROID").setTextColor(Color.WHITE));
        mArticleList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        mRlvMain.setLayoutManager(mLayoutManager);
        mRlvMain.setAdapter(mArticleAdapter);
        mNetHander = new NetHander((Activity) mContext);
        initData();
        setListener();
        return view;
    }


    private void setListener() {

        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageCount = 0;
                if (key != null && key.length() > 1) {
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {
                    initData();
                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageCount++;
                if (key != null && key.length() > 1) {
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {
                    initData();
                }

            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = mEtSearch.getText().toString().trim();
                //有关键词则搜索
                if (key.length() > 1) {
                    pageCount = 0;
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {//没有默认显示最新文章
                    initData();
                }
            }
        });


        mArticleAdapter.setArticleClickListener(new ArticleAdapter.ArticleClickListener() {
            @Override
            public void articleClick(String url) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", url);
                mContext.startActivity(mIntent);
            }
        });
        mVpBanner.setBannerClickLisener(new BannerPager.BannerClickLisener() {
            @Override
            public void bannerClick(String url) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", url);
                mContext.startActivity(mIntent);
            }
        });
    }

    private void initData() {

        mBannerThread = new BannerThread(mContext, mNetHander);
        mBannerThread.start();

        mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
        mArticleListThread.start();
    }

    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case L.CODE.MSG_MAIN_BANNER_SUCCESS:
                    BannerBean banner = (BannerBean) msg.obj;
                    initBanner(banner);
                    break;
                case L.CODE.MSG_MAIN_ARTICLE_SUCCESS:
                    ArticleBean article = (ArticleBean) msg.obj;
                    initArticle(article);

                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }

            mSrlRoot.finishLoadMore();
            mSrlRoot.finishRefresh();
        }
    }

    private void initArticle(ArticleBean article) {
        mTvTips.setVisibility(View.VISIBLE);
        List<ArticleBean.Datas> mDatas = article.getData().getDatas();
        mArticleAdapter.refreshData(mDatas);
    }

    private void initBanner(BannerBean banner) {
        mVpBanner.startPlayLoop(banner.getData());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
