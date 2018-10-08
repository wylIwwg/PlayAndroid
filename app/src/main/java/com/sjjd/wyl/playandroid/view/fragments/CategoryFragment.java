package com.sjjd.wyl.playandroid.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.CateAdapter;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.model.utils.L;
import com.sjjd.wyl.playandroid.thread.CategoryThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    private static final String TAG = "CategoryFragment ";
    List<CategoryBean.Data> list;
    CateAdapter mCategoryAdapter;
    CategoryThread mCategoryThread;
    Context mContext;
    NetHander mNetHander;
    @BindView(R.id.rgGroup)
    RadioGroup mRgGroup;
    @BindView(R.id.rlvCategory)
    RecyclerView mRlvCategory;
    Unbinder unbinder;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        mNetHander = new NetHander(getActivity());
        mSrlRoot.setEnableLoadMore(false);
        initData();
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        mCategoryAdapter = new CateAdapter(new ArrayList<CategoryBean.Children>(), mContext);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRlvCategory.addItemDecoration(new SpaceItemDecoration(20));
        mRlvCategory.setLayoutManager(flowLayoutManager);
        mRlvCategory.setAdapter(mCategoryAdapter);

        mCategoryThread = new CategoryThread(mContext, mNetHander);
        mCategoryThread.start();

        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mCategoryThread = new CategoryThread(mContext, mNetHander);
                mCategoryThread.start();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSrlRoot.finishRefresh();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            RadioButton rb = (RadioButton) compoundButton;
            int index = (int) rb.getTag();
            mCategoryAdapter.refreshData(child.get(index));
        }
    }


    class NetHander extends Handler {

        private WeakReference<Activity> mReference;

        public NetHander(Activity reference) {
            mReference = new WeakReference<Activity>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case L.CODE.MSG_CATEGORY_SUCCESS:
                    List<CategoryBean.Data> cate = (List<CategoryBean.Data>) msg.obj;
                    if (cate.size() > 0) {
                        initCategory(cate);
                    }
                    mSrlRoot.finishRefresh();
                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }
        }
    }

    List<List<CategoryBean.Children>> child;

    private void initCategory(List<CategoryBean.Data> cate) {

        child = new ArrayList<>();
        for (int i = 0; i < cate.size(); i++) {
            RadioButton rb = new RadioButton(mContext);
            RadioGroup.LayoutParams l = new RadioGroup.LayoutParams(-1, 100);
            rb.setLayoutParams(l);
            rb.setBackgroundResource(R.drawable.rb_checkstatus);
            rb.setTextSize(20);
            rb.setTextColor(mContext.getResources().getColorStateList(R.color.rb_category));
            rb.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            rb.setGravity(Gravity.CENTER);
            CategoryBean.Data mData = cate.get(i);
            rb.setText(mData.getName());
            rb.setTag(i);
            child.add(new ArrayList<CategoryBean.Children>());
            child.set(i, mData.getChildren());
            rb.setOnCheckedChangeListener(this);
            mRgGroup.addView(rb);
            if (i == 0) {
                rb.setChecked(true);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
