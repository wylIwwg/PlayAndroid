package com.sjjd.wyl.playandroid.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.ProjectAdapter;
import com.sjjd.wyl.playandroid.bean.ProjectBean;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {


    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.rlvProject)
    RecyclerView mRlvProject;
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;
    Unbinder unbinder;

    private int pageCount;
    private Context mContext;
    ProjectAdapter mProjectAdapter;
    private LinearLayoutManager mLayoutManager;
    List<ProjectBean.Datas> mDatas;


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
        init();
        return view;
    }

    private void init() {
        mDatas = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mProjectAdapter = new ProjectAdapter(mContext, mDatas);
        mRlvProject.setAdapter(mProjectAdapter);
        mRlvProject.setLayoutManager(mLayoutManager);

        Log.e(TAG, "init: ");
        OkGo.<ProjectBean>get("http://www.wanandroid.com/project/list/0/json?cid=294")
                .tag(this)
                .execute(new JsonCallBack<ProjectBean>(ProjectBean.class) {
                    @Override
                    public void onSuccess(Response<ProjectBean> response) {
                        ProjectBean bean = response.body();
                        Log.e(TAG, "onSuccess: " + bean.getErrorMsg());
                        initProject(bean.getData().getDatas());
                    }

                    @Override
                    public void onError(Response<ProjectBean> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: " + response.body());
                    }
                });
    }

    private void initProject(List<ProjectBean.Datas> datas) {
        mProjectAdapter.refreshData(datas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
