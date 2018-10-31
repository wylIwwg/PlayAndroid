package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.CategoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/5/2.
 */

public class CateAdapter extends RecyclerView.Adapter {
    List<CategoryBean.Children> data;
    Context mContext;

    int[] mColors = new int[]
            {
                    R.drawable.tv_keys, R.drawable.tv_keys1, R.drawable.tv_keys2,
                    R.drawable.tv_keys3, R.drawable.tv_keys4, R.drawable.tv_keys5,
                    R.drawable.tv_keys6
            };


    public CateAdapter(List<CategoryBean.Children> data, Context context) {
        this.data = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = View.inflate(mContext, R.layout.item_hotkeys, null);
        //   View mView = LayoutInflater.from(mContext).inflate(R.layout.item_hotkeys, null, false);
        //    RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        //   mView.setLayoutParams(l);
        return new KeysHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        KeysHolder mHolder = (KeysHolder) holder;
        final CategoryBean.Children mHotWords = data.get(position);
        mHolder.mTvKeys.setText(mHotWords.getName());
        mHolder.mTvKeys.setBackgroundResource(mColors[position % mColors.length]);

        mHolder.mTvKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeyClickListener != null) {
                    mKeyClickListener.keyClick(mHotWords);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void refreshData(List<CategoryBean.Children> keys) {
        if (data != null) {
            data.clear();
            data.addAll(keys);
            notifyDataSetChanged();
        }
    }

    public interface KeyClickListener {
        void keyClick(CategoryBean.Children key);
    }

    KeyClickListener mKeyClickListener;

    public void setKeyClickListener(KeyClickListener listener) {
        mKeyClickListener = listener;
    }

    static class KeysHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvKeys)
        TextView mTvKeys;

        KeysHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
