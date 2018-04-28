package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.bean.BannerBean;

import java.util.List;

/**
 * Created by wyl on 2018/4/27.
 */

public class BannerAdapter extends PagerAdapter {
    private String TAG = "BannerAdapter";
    Context mContext;
    List<BannerBean.Data> mList;

    public BannerAdapter(Context context, List<BannerBean.Data> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_banner, null);
        ImageView img = view.findViewById(R.id.imgCover);
        TextView tv = view.findViewById(R.id.tvTitle);
        final BannerBean.Data mData = mList.get(position);

        tv.setText(mData.getTitle());
        Log.e(TAG, "instantiateItem: " + mData.getImagepath());
        Glide.with(mContext)
                .load(mData.getImagepath())
                .into(img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mData.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
