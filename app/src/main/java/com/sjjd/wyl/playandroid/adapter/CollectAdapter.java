package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.CollectBean;
import com.sjjd.wyl.playandroid.view.activities.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/4/28.
 */

public class CollectAdapter extends PagerAdapter {
    Context mContext;
    List<CollectBean.Datas> mDatas;

    public CollectAdapter(Context context, List<CollectBean.Datas> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void refreshData(List<CollectBean.Datas> datas, boolean more) {
        if (mDatas != null) {
            if (more) {
                mDatas.addAll(datas);
            } else {
                mDatas.clear();
                mDatas.addAll(datas);
            }
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_collect, null, false);
        RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(l);
        CollectHolder h = new CollectHolder(mView);
        final CollectBean.Datas data = this.mDatas.get(position);
        h.mTvTitle.setText(Html.fromHtml(data.getTitle()));
        h.mTvAuthor.setText("作者：" + data.getAuthor());
        h.mTvTime.setText("收藏时间：" + data.getNiceDate());
        h.mTvCategory.setText("分类：" + data.getChapterName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.blank)
                .error(R.drawable.blank)
                .priority(Priority.HIGH);
        Glide.with(mContext).load(data.getEnvelopePic()).apply(options).into(h.mImgCover);
        h.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, WebActivity.class);
                mIntent.putExtra("url", data.getLink());
                mContext.startActivity(mIntent);
            }
        });

        mView.setTag(h);
        container.addView(mView);

        return mView;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return object == view;
    }

    static class CollectHolder {
        @BindView(R.id.imgCover)
        ImageView mImgCover;
        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.tvAuthor)
        TextView mTvAuthor;
        @BindView(R.id.tvTime)
        TextView mTvTime;
        @BindView(R.id.cardCollect)
        CardView mCardView;
        @BindView(R.id.tvCategory)
        TextView mTvCategory;

        CollectHolder(View view) {

            ButterKnife.bind(this, view);
        }
    }
}
