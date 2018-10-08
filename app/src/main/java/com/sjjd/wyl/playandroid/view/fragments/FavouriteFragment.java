package com.sjjd.wyl.playandroid.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.CollectAdapter;
import com.sjjd.wyl.playandroid.bean.CollectBean;
import com.sjjd.wyl.playandroid.model.utils.L;
import com.sjjd.wyl.playandroid.thread.JsonCallBack;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    Context mContext;

    List<CollectBean.Datas> list;
    LinearLayoutManager mLayoutManager;
    CollectAdapter mCollectAdapter;

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.srlRoot)
    SmartRefreshLayout mSrlRoot;
    Unbinder unbinder;
    @BindView(R.id.rlvCollect)
    SwipeMenuRecyclerView mRlvCollect;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListener();
        initData();
        return view;
    }

    private void initListener() {
        list = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mCollectAdapter = new CollectAdapter(mContext, list);
        // 设置监听器。
        mRlvCollect.setSwipeMenuCreator(mSwipeMenuCreator);
        mRlvCollect.setSwipeMenuItemClickListener(mMenuItemClickListener);

        mRlvCollect.setAdapter(mCollectAdapter);
        mRlvCollect.setLayoutManager(mLayoutManager);

        mSrlRoot.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });


    }

    private void initData() {
        String url = String.format(L.URL.Get_Collect, 0);
        OkGo.<CollectBean>get(url)
                .tag(this)
                .execute(new JsonCallBack<CollectBean>(CollectBean.class) {
                    @Override
                    public void onSuccess(Response<CollectBean> response) {
                        CollectBean mBody = response.body();
                        Log.e(TAG, "onSuccess: " + mBody);
                        if (mBody != null && mBody.getData().getDatas() != null) {
                            mCollectAdapter.refreshData(mBody.getData().getDatas());
                        }

                    }

                    @Override
                    public void onError(Response<CollectBean> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: ");
                    }
                });


    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = 200;

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem addItem = new SwipeMenuItem(getContext())
                    .setBackgroundColor(mContext.getResources().getColor(R.color.seagreen))
                    .setWidth(width)
                    .setText("取消收藏")
                    .setTextSize(20)
                    .setTextColor(mContext.getResources().getColor(R.color.red))
                    .setHeight(height);
            rightMenu.addMenuItem(addItem); // 添加菜单到右侧。

        }
    };
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            Log.e(TAG, "onItemClick: " + adapterPosition);

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
