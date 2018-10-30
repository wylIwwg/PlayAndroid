package com.sjjd.wyl.playandroid.view.fragments;


import android.content.Context;
import android.graphics.Color;
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
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.ProjectAdapter;
import com.sjjd.wyl.playandroid.base.BaseFragment;
import com.sjjd.wyl.playandroid.bean.ProjectBean;
import com.sjjd.wyl.playandroid.presenter.IProjectPrestener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment<ProjectBean> {


    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.rlvProject)
    RecyclerView mRlvProject;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;
    Unbinder unbinder;

    private int pageCount=0;
    private Context mContext;
    ProjectAdapter mProjectAdapter;
    private LinearLayoutManager mLayoutManager;
    List<ProjectBean.Datas> mDatas;

    IProjectPrestener<ProjectBean> mPrestener;

    boolean isLoadMore = false;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        mSrlRoot.setRefreshHeader(new StoreHouseHeader(getActivity()).initWithString("PLAY ANDROID").setTextColor(Color.WHITE));
        mPrestener = new IProjectPrestener<ProjectBean>(this);
        init();
        return view;
    }

    private void init() {
        mDatas = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mProjectAdapter = new ProjectAdapter(mContext, mDatas);
        mRlvProject.setAdapter(mProjectAdapter);
        mRlvProject.setLayoutManager(mLayoutManager);
        mRlvProject.addItemDecoration(new SpaceItemDecoration(5));
        mPrestener.getProject(mContext,pageCount);


        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageCount = 0;
                mPrestener.getProject(mContext,pageCount);
                isLoadMore = false;
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageCount++;
                mPrestener.getProject(mContext,pageCount);
                isLoadMore = true;

            }
        });

    }

    private void initProject(List<ProjectBean.Datas> datas) {
        mProjectAdapter.refreshData(datas, isLoadMore);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(mContext, "" + error, Toast.LENGTH_SHORT).show();
        //关闭加载刷新
        mSrlRoot.finishLoadMore();
        mSrlRoot.finishRefresh();
        isLoadMore = false;
    }

    @Override
    public void onSuccess(ProjectBean result) {

        if (result.getData().getDatas() != null) {
            initProject(result.getData().getDatas());
        }
        //关闭加载刷新
        mSrlRoot.finishLoadMore();
        mSrlRoot.finishRefresh();
        isLoadMore = false;
    }
}
