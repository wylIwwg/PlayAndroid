package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.view.activities.WebActivity;
import com.sjjd.wyl.playandroid.bean.CollectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/4/28.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CollectBean.Datas> mDatas;

    public CollectAdapter(Context context, List<CollectBean.Datas> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_collect, null, false);
        RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(l);
        return new CollectHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectHolder h = (CollectHolder) holder;
        final CollectBean.Datas data = this.mDatas.get(position);
        h.mTvTitle.setText(Html.fromHtml(data.getTitle()));
        h.mTvAuthor.setText("作者：" + data.getAuthor());
        h.mTvTime.setText("收藏时间：" + data.getNiceDate());
        h.mTvCategory.setText("分类：" + data.getChapterName());
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", data.getLink());
                mContext.startActivity(mIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void refreshData(List<CollectBean.Datas> datas) {
        if (mDatas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    static class CollectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.tvAuthor)
        TextView mTvAuthor;
        @BindView(R.id.tvTime)
        TextView mTvTime;
        @BindView(R.id.tvCategory)
        TextView mTvCategory;

        CollectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
