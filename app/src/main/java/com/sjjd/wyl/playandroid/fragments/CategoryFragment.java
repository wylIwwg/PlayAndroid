package com.sjjd.wyl.playandroid.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.CategoryAdapter;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.net.L;
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
public class CategoryFragment extends Fragment {


    @BindView(R.id.elvCategory)
    ExpandableListView mElvCategory;
    Unbinder unbinder;

    List<CategoryBean.Data> list;
    CategoryAdapter mCategoryAdapter;
    CategoryThread mCategoryThread;
    Context mContext;
    NetHander mNetHander;

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
        initData();
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(mContext, list);
        mElvCategory.setGroupIndicator(null);
        mElvCategory.setAdapter(mCategoryAdapter);
        mCategoryThread = new CategoryThread(mContext, mNetHander);
        mCategoryThread.start();
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
                    initCategory(cate);
                    break;
                case L.CODE.MSG_DATA_FAILED:
                    break;
            }
        }

    }

    private void initCategory(List<CategoryBean.Data> cate) {
        mCategoryAdapter.refreshData(cate);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
