package com.sjjd.wyl.playandroid.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.activities.WebActivity;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.adapter.BannerPager;
import com.sjjd.wyl.playandroid.adapter.HotWordsAdapter;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.bean.BannerBean;
import com.sjjd.wyl.playandroid.bean.HotWords;
import com.sjjd.wyl.playandroid.net.L;
import com.sjjd.wyl.playandroid.thread.ArticleListThread;
import com.sjjd.wyl.playandroid.thread.BannerThread;
import com.sjjd.wyl.playandroid.thread.KeyWorsThread;

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
    private final String TAG = this.getClass().getSimpleName();
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
    ArticleListThread mArticleListThread;//文章线程
    ArticleListThread mArticleSearchThread;//搜索热词线程
    KeyWorsThread mKeyWorsThread;

    NetHander mNetHander;
    LinearLayoutManager mLayoutManager;

    ArticleAdapter mArticleAdapter;
    HotWordsAdapter mWordsAdapter;

    List<ArticleBean.Datas> mArticleList;//文章集合
    List<HotWords.Data> keysList;//全部热词
    Context mContext;
    int pageCount = 0;//页数

    String key = "";//搜索关键词
    PopupWindow pop;//热词显示

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
        mWordsAdapter = new HotWordsAdapter(keysList = new ArrayList<>(), mContext);
        mRlvMain.setLayoutManager(mLayoutManager);
        mRlvMain.setAdapter(mArticleAdapter);
        mNetHander = new NetHander((Activity) mContext);
        initData();
        setListener();
        return view;
    }

    boolean isShowing = false;

    private void setListener() {

        //搜索框点击监听 显示热词
        mEtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing) {
                    pop.dismiss();
                    isShowing = false;
                } else {
                    isShowing = true;
                    if (pop == null)
                        showKeysPop();
                    else pop.showAsDropDown(mEtSearch);
                }
            }
        });


        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageCount = 0;
              /*  if (key != null && key.length() > 1) {
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                    mArticleListThread.start();
                }*/
                mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                mArticleListThread.start();
                key = "";
                mEtSearch.setText("");

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageCount++;
                if (key != null && key.length() > 1) {
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                    mArticleListThread.start();
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
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                    mArticleListThread.start();
                }
            }
        });


        mWordsAdapter.setKeyClickListener(new HotWordsAdapter.KeyClickListener() {
            @Override
            public void keyClick(String key) {
                mEtSearch.setText(key);
                MainFragment.this.key = key;
                //有关键词则搜索
                if (key.length() > 1) {
                    pageCount = 0;
                    mArticleSearchThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleSearchThread.start();
                } else {//没有默认显示最新文章
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                    mArticleListThread.start();
                }
                if (pop.isShowing()) {
                    pop.dismiss();
                }
            }
        });

        mArticleAdapter.setArticleClickListener(new ArticleAdapter.ArticleClickListener() {
            @Override
            public void articleClick(String url) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", url);
                mContext.startActivity(mIntent);
                if (pop != null && isShowing) {
                    pop.dismiss();
                    isShowing = false;
                }
            }
        });
        mVpBanner.setBannerClickLisener(new BannerPager.BannerClickLisener() {
            @Override
            public void bannerClick(String url) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", url);
                mContext.startActivity(mIntent);
                if (pop != null && isShowing) {
                    pop.dismiss();
                    isShowing = false;
                }
            }
        });
    }

    //开启线程 加载数据
    private void initData() {

        mKeyWorsThread = new KeyWorsThread(mContext, mNetHander);
        mKeyWorsThread.start();

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
                case L.CODE.MSG_MAIN_HOTWORDS_SUCCESS:
                    HotWords keys = (HotWords) msg.obj;
                    mWordsAdapter.refreshData(keys.getData());

                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }
            //关闭加载刷新
            mSrlRoot.finishLoadMore();
            mSrlRoot.finishRefresh();
        }

    }

    private void showKeysPop() {

        View view = View.inflate(mContext, R.layout.item_hotwords, null);
        pop = new PopupWindow(view, -1,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置属性配置 否则外部Editview无法同时编辑输入
        pop.setFocusable(false);// 点击back退出pop
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setAnimationStyle(R.style.PopupAnimation);
        pop.setBackgroundDrawable(new ColorDrawable(0xffffffff));

        RecyclerView mRlVKeys = view.findViewById(R.id.rlvHotWords);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRlVKeys.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        mRlVKeys.setLayoutManager(flowLayoutManager);
        mRlVKeys.setAdapter(mWordsAdapter);
        if (!pop.isShowing()) {
            pop.showAsDropDown(mEtSearch);
        }
        isShowing = true;

    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
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
        //关闭图片轮播
        mVpBanner.stopPlayLoop();
    }
}
