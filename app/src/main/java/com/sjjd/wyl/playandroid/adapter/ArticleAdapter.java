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
import android.widget.Toast;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.base.App;
import com.sjjd.wyl.playandroid.bean.ArticleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/4/28.
 */

public class ArticleAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<ArticleBean.Datas> mDatas;

    public ArticleAdapter(Context context, List<ArticleBean.Datas> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_article, null, false);
        RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(l);
        return new ArticleHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArticleHolder h = (ArticleHolder) holder;
        final ArticleBean.Datas data = this.mDatas.get(position);
        h.mTvTitle.setText(Html.fromHtml(data.getTitle()));
        h.mTvAuthor.setText("作者：" + data.getAuthor());
        h.mTvCategory.setText("分类：" + data.getChapterName());
        h.mTvTime.setText("时间：" + data.getNiceDate());

        if (App.getInstance().logined)
            h.mCbCollect.setChecked(data.getCollect());

        h.mCbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (App.getInstance().logined) {
                    if (isChecked) {

                    } else {

                    }
                } else {
                    Toast.makeText(mContext, "请先登录再来收藏吧~", Toast.LENGTH_SHORT).show();
                }

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

    public void refreshData(List<ArticleBean.Datas> datas, boolean isMore) {
        if (mDatas != null) {
            if (isMore) {
                mDatas.addAll(datas);
            } else {
                mDatas.clear();
                mDatas.addAll(datas);
            }
            notifyDataSetChanged();
        }
    }

    static class ArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.tvAuthor)
        TextView mTvAuthor;
        @BindView(R.id.tvCategory)
        TextView mTvCategory;
        @BindView(R.id.tvTime)
        TextView mTvTime;
        @BindView(R.id.cbCollect)
        CheckBox mCbCollect;

        ArticleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
