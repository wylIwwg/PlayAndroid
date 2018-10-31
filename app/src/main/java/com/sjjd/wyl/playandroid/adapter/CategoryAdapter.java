package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.CategoryBean;
import com.sjjd.wyl.playandroid.view.activities.ArticlesActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/5/2.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CategoryBean.Data> groupList;


    RelativeLayout lastView;


    public CategoryAdapter(Context context, List<CategoryBean.Data> groupList) {
        mContext = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = View.inflate(mContext, R.layout.item_category, null);
        mView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return new ViewHolder(mView);
    }

    Button lastBtn;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder mHolder = (ViewHolder) holder;
        final CategoryBean.Data mData = groupList.get(position);
        mHolder.mBtnCategory.setText(mData.getName());
        mHolder.mRlvChild.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                rv.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mHolder.mBtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHolder.mLayout.getVisibility() == View.VISIBLE) {
                    mHolder.mLayout.setVisibility(View.GONE);
                    mHolder.mBtnCategory.setTextColor(mContext.getResources().getColor(R.color.darkseagreen));
                    return;
                }
                if (lastView != null) {
                    lastView.setVisibility(View.GONE);
                }
                mHolder.mBtnCategory.setTextColor(mContext.getResources().getColor(R.color.seagreen));
                mHolder.mLayout.setVisibility(View.VISIBLE);

                if (lastBtn != null && lastBtn != mHolder.mBtnCategory) {
                    lastBtn.setTextColor(mContext.getResources().getColor(R.color.darkseagreen));
                }

                if (lastView == mHolder.mLayout) {
                    return;
                }
                lastView = mHolder.mLayout;
                lastBtn = mHolder.mBtnCategory;
                FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
                mHolder.mRlvChild.setLayoutManager(flowLayoutManager);
                mHolder.mRlvChild.addItemDecoration(new SpaceItemDecoration(5));
                final CateAdapter mCateAdapter = new CateAdapter(mData.getChildren(), mContext);
                mCateAdapter.setKeyClickListener(new CateAdapter.KeyClickListener() {
                    @Override
                    public void keyClick(CategoryBean.Children key) {
                        Intent mIntent = new Intent(mContext, ArticlesActivity.class);
                        mIntent.putExtra("cid", key.getId());
                        mIntent.putExtra("title", key.getName());
                        mContext.startActivity(mIntent);
                    }
                });
                mHolder.mRlvChild.setAdapter(mCateAdapter);
            }
        });

    }


    @Override
    public int getItemCount() {
        return groupList == null ? 0 : groupList.size();

    }


    public void refreshData(List<CategoryBean.Data> cate) {
        if (groupList != null) {
            groupList.clear();
            groupList.addAll(cate);
            notifyDataSetChanged();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnCategory)
        Button mBtnCategory;
        @BindView(R.id.rlChild)
        RelativeLayout mLayout;
        @BindView(R.id.rlvChild)
        RecyclerView mRlvChild;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
