package com.sjjd.wyl.playandroid.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.library.flowlayout.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.CategoryAdapter;
import com.sjjd.wyl.playandroid.base.BaseFragment;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.presenter.PrestenerCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment<CategoryBean> {

    private static final String TAG = "CategoryFragment ";
    List<CategoryBean.Data> list;
    Context mContext;
    @BindView(R.id.rlvCategory)
    RecyclerView mRlvCategory;
    Unbinder unbinder;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;

    PrestenerCategory<CategoryBean> mPrestener;
    CategoryAdapter mCategoryAdapter;


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
        mPrestener = new PrestenerCategory<>(this);
        mPrestener.getCategory(mContext);

        mSrlRoot.setEnableLoadMore(false);
        initData();
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(mContext, list);
        LinearLayoutManager flowLayoutManager = new LinearLayoutManager(mContext);
        mRlvCategory.addItemDecoration(new SpaceItemDecoration(20));
        mRlvCategory.setLayoutManager(flowLayoutManager);
        mRlvCategory.setAdapter(mCategoryAdapter);

        mSrlRoot.setEnableLoadMore(false);

        mSrlRoot.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPrestener.getCategory(mContext);
            }
        });
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
        mSrlRoot.finishRefresh();
    }

    @Override
    public void onSuccess(CategoryBean result) {
        super.onSuccess(result);
        List<CategoryBean.Data> cate = result.getData();
        if (cate.size() > 0) {
            initCategory(cate);
        }
        mSrlRoot.finishRefresh();

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
