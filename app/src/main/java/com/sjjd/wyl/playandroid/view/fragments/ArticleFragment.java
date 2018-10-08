package com.sjjd.wyl.playandroid.view.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.view.activities.WebActivity;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.model.utils.L;
import com.sjjd.wyl.playandroid.thread.ArticleListThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    KeyBroadReceiver mKeyBroadReceiver;
    @BindView(R.id.rvlArticle)
    RecyclerView mRvlArticle;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;
    Unbinder unbinder;
    private int pageCount;
    private ArticleListThread mArticleListThread;
    private Context mContext;
    ArticleAdapter mArticleAdapter;
    List<ArticleBean.Datas> mArticleList;//文章集合
    private LinearLayoutManager mLayoutManager;
    NetHander mNetHander;
    String key = "";//搜索关键词

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        init();
        initListener();
        return view;
    }

    private void init() {
        IntentFilter mIntentFilter = new IntentFilter(L.BROADCAST.KEY);
        mKeyBroadReceiver = new KeyBroadReceiver();
        mContext.registerReceiver(mKeyBroadReceiver, mIntentFilter);
//        mSrlRoot.setRefreshHeader(new StoreHouseHeader(getActivity()).initWithString("PLAY　ANDROID").setTextColor(Color.WHITE));

        mNetHander = new NetHander(getActivity());
        mArticleList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mArticleAdapter = new ArticleAdapter(mContext, mArticleList);
        mRvlArticle.setAdapter(mArticleAdapter);
        mRvlArticle.setLayoutManager(mLayoutManager);

        mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
        mArticleListThread.start();
    }

    private void initListener() {

        mArticleAdapter.setArticleClickListener(new ArticleAdapter.ArticleClickListener() {
            @Override
            public void articleClick(String url) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", url);
                mContext.startActivity(mIntent);
            }
        });

        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageCount = 0;
                key = "";
                mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                mArticleListThread.start();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageCount++;
                if (key != null && key.length() > 1) {
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                    mArticleListThread.start();
                } else {
                    mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount);
                    mArticleListThread.start();
                }

            }
        });


    }

    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case L.CODE.MSG_MAIN_ARTICLE_SUCCESS:
                    ArticleBean article = (ArticleBean) msg.obj;
                    initArticle(article);

                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }
            //关闭加载刷新
            mSrlRoot.finishLoadMore();
            mSrlRoot.finishRefresh();
        }
    }

    private void initArticle(ArticleBean article) {
        List<ArticleBean.Datas> mDatas = article.getData().getDatas();
        mArticleAdapter.refreshData(mDatas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mContext.unregisterReceiver(mKeyBroadReceiver);
    }


    class KeyBroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(L.BROADCAST.KEY)) {
                String key = intent.getStringExtra("key");
                Log.e(TAG, "onReceive: " + key);
                ArticleFragment.this.key = key;
                pageCount = 0;
                mArticleListThread = new ArticleListThread(mContext, mNetHander, pageCount, key);
                mArticleListThread.start();
            }
        }
    }
}
