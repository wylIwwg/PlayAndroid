package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.ProjectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by wyl on 2018/4/28.
 */

public class ProjectAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<ProjectBean.Datas> mDatas;

    public ProjectAdapter(Context context, List<ProjectBean.Datas> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_project, null, false);
        RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(l);
        return new ProjectHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectHolder h = (ProjectHolder) holder;
        final ProjectBean.Datas data = this.mDatas.get(position);
        h.mTvTitle.setText(Html.fromHtml(data.getTitle()));
        h.mTvAuthor.setText("作者：" + data.getAuthor());
        h.mTvTime.setText("时间：" + data.getNiceDate());
        h.mCbCollect.setChecked(data.getCollect());
        h.mTvDesc.setText(data.getDesc());
        Glide.with(mContext).load(data.getEnvelopePic()).error(R.drawable.left_arrow).into(h.mGifCover);
        h.mCbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArticleClickListener != null) {
                    mArticleClickListener.articleClick(data.getLink());
                }
            }
        });

    }

    public interface ArticleClickListener {
        void articleClick(String url);
    }

    ArticleClickListener mArticleClickListener;

    public void setArticleClickListener(ArticleClickListener listener) {
        mArticleClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void refreshData(List<ProjectBean.Datas> datas) {
        if (mDatas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    static class ProjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.tvAuthor)
        TextView mTvAuthor;
        @BindView(R.id.tvTime)
        TextView mTvTime;
        @BindView(R.id.cbCollect)
        CheckBox mCbCollect;
        @BindView(R.id.tvDesc)
        TextView mTvDesc;
        @BindView(R.id.imgCover)
        GifImageView mGifCover;

        ProjectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
