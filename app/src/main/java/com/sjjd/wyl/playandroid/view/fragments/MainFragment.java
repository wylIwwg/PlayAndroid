package com.sjjd.wyl.playandroid.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.ArticleAdapter;
import com.sjjd.wyl.playandroid.adapter.BannerPager;
import com.sjjd.wyl.playandroid.adapter.HotWordsAdapter;
import com.sjjd.wyl.playandroid.bean.ArticleBean;
import com.sjjd.wyl.playandroid.bean.BannerBean;
import com.sjjd.wyl.playandroid.bean.HotWords;
import com.sjjd.wyl.playandroid.thread.ArticleListThread;
import com.sjjd.wyl.playandroid.thread.BannerThread;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.thread.KeyWorsThread;
import com.sjjd.wyl.playandroid.view.activities.WebActivity;

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
    Unbinder unbinder;

    BannerThread mBannerThread;
    ArticleListThread mArticleSearchThread;//搜索热词线程
    KeyWorsThread mKeyWorsThread;

    NetHander mNetHander;
    LinearLayoutManager mLayoutManager;

    ArticleAdapter mArticleAdapter;
    HotWordsAdapter mWordsAdapter;

    List<HotWords.Data> keysList;//全部热词
    Context mContext;
    List<Fragment> mainFragments;

    String key = "";//搜索关键词
    PopupWindow pop;//热词显示
    @BindView(R.id.rbArticle)
    RadioButton mRbArticle;
    @BindView(R.id.rbProject)
    RadioButton mRbProject;
    @BindView(R.id.vpContent)
    ViewPager mVpContent;
    @BindView(R.id.rgGroup)
    RadioGroup mRgGroup;
    @BindView(R.id.rlSearch)
    RelativeLayout mRlSearch;


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
        mLayoutManager = new LinearLayoutManager(mContext);
        mWordsAdapter = new HotWordsAdapter(keysList = new ArrayList<>(), mContext);
        mNetHander = new NetHander((Activity) mContext);
        initData();
        setListener();
        return view;
    }

    boolean isShowing = false;

    private void setListener() {

        mRgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbArticle:
                        mVpContent.setCurrentItem(0);
                        mRlSearch.startAnimation(mMaxAnimation);
                        mRlSearch.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbProject:
                        mVpContent.setCurrentItem(1);
                        mRlSearch.startAnimation(mMinAnimation);
                        mRlSearch.setVisibility(View.GONE);
                        break;
                }
            }
        });

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


        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = mEtSearch.getText().toString().trim();
                //有关键词则搜索
                if (key.length() > 1) {
                    Intent mIntent = new Intent(I.BROADCAST.KEY);
                    mIntent.putExtra("key", key);
                    mContext.sendBroadcast(mIntent);
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
                    //
                    Intent mIntent = new Intent(I.BROADCAST.KEY);
                    mIntent.putExtra("key", key);
                    mContext.sendBroadcast(mIntent);
                }
                if (pop.isShowing()) {
                    pop.dismiss();
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


        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mVpContent.setCurrentItem(position);
                if (position == 0) {
                    mRlSearch.startAnimation(mMaxAnimation);
                    mRbArticle.setChecked(true);
                    mRbProject.setChecked(false);
                    mRlSearch.setVisibility(View.VISIBLE);
                } else {
                    mRlSearch.startAnimation(mMinAnimation);
                    mRlSearch.setVisibility(View.GONE);
                    mRbArticle.setChecked(false);
                    mRbProject.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    ArticleFragment mArticleFragment;
    ProjectFragment mProjectFragment;
    Animation mMaxAnimation;
    Animation mMinAnimation;

    //开启线程 加载数据
    private void initData() {

        mMaxAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.search_max);
        mMaxAnimation.setFillAfter(true);
        mMinAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.search_min);
        mMinAnimation.setFillAfter(true);


        mRgGroup.check(R.id.rbArticle);

        mainFragments = new ArrayList<>();
        mArticleFragment = new ArticleFragment();
        mProjectFragment = new ProjectFragment();
        mainFragments.add(mArticleFragment);
        mainFragments.add(mProjectFragment);
        mVpContent.setAdapter(new ContentAdapter(getActivity().getSupportFragmentManager()));
        mVpContent.setCurrentItem(0);

        mKeyWorsThread = new KeyWorsThread(mContext, mNetHander);
        mKeyWorsThread.start();

        mBannerThread = new BannerThread(mContext, mNetHander);
        mBannerThread.start();

    }

    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case I.CODE.MSG_MAIN_BANNER_SUCCESS:
                    BannerBean banner = (BannerBean) msg.obj;
                    initBanner(banner);
                    break;
                case I.CODE.MSG_MAIN_ARTICLE_SUCCESS:
                    //ArticleBean article = (ArticleBean) msg.obj;
                   // initArticle(article);

                    break;
                case I.CODE.MSG_MAIN_HOTWORDS_SUCCESS:
                    HotWords keys = (HotWords) msg.obj;
                    mWordsAdapter.refreshData(keys.getData());

                    break;
                case I.CODE.MSG_DATA_FAILED:
                    break;
            }
            //关闭加载刷新
        }

    }

    ///显示关键词
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
        mRlVKeys.addItemDecoration(new SpaceItemDecoration(dp2px(5)));
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
        List<ArticleBean.Datas> mDatas = article.getData().getDatas();
        mArticleAdapter.refreshData(mDatas,false);
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


    class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mainFragments.get(position);
        }

        @Override
        public int getCount() {
            return mainFragments.size();
        }
    }
}
