package com.sjjd.wyl.playandroid.view.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.adapter.CollectAdapter;
import com.sjjd.wyl.playandroid.base.BaseFragment;
import com.sjjd.wyl.playandroid.bean.CollectBean;
import com.sjjd.wyl.playandroid.presenter.PrestenerCollect;
import com.sjjd.wyl.playandroid.utils.BitmapUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends BaseFragment<CollectBean> {
    Context mContext;

    List<CollectBean.Datas> list;
    LinearLayoutManager mLayoutManager;
    CollectAdapter mCollectAdapter;

    private final String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;

    Bitmap blurNormal;

    int page = 0;

    PrestenerCollect<CollectBean> mPrestener;
    @BindView(R.id.imgBlur)
    ImageView mImgBlur;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

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
        mPrestener = new PrestenerCollect<>(this);
        initListener();
        initData();
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inScaled = false;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pkq_b, mOptions);
        blurNormal = BitmapUtils.rsBlur(mContext, mBitmap, 8);
        return view;
    }

    private void initListener() {
        list = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mContext);
        mCollectAdapter = new CollectAdapter(mContext, list);

        mViewpager.setAdapter(mCollectAdapter);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setPageMargin(10);
        mViewpager.setPageTransformer(true, new ScaleInTransformer(0.85f));


        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CollectBean.Datas mDatas = list.get(position);
                final String mEnvelopePic = mDatas.getEnvelopePic();
                if (mEnvelopePic != null && mEnvelopePic.length() > 0) {
                    Glide.with(mContext)
                            .load(mEnvelopePic)
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    BitmapDrawable mBitmapDrawable = (BitmapDrawable) resource;
                                    PaletteMainColor(mBitmapDrawable.getBitmap());
                                }
                            });

                } else {
                    PaletteMainColor(blurNormal);

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 设置监听器。
        //  mRlvCollect.setSwipeMenuCreator(mSwipeMenuCreator);
        //  mRlvCollect.setSwipeMenuItemClickListener(mMenuItemClickListener);

        //   mRlvCollect.setAdapter(mCollectAdapter);
        //    mRlvCollect.setLayoutManager(mLayoutManager);


    }

    private void PaletteMainColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                Palette.Swatch vibrant =
                        palette.getLightVibrantSwatch();

                Palette.Swatch mMutedSwatch = palette.getLightMutedSwatch();
                if (vibrant != null) {
                    // If we have a vibrant color
                    // update the title TextView
                    mImgBlur.setBackgroundColor(
                            vibrant.getRgb());
                }
            }
        });
    }


    @Override
    public void onSuccess(CollectBean result) {
        super.onSuccess(result);
        List<CollectBean.Datas> mDatas = result.getData().getDatas();
        list.addAll(mDatas);
        mCollectAdapter.refreshData(mDatas, isMore);
        isMore = false;
        mViewpager.setCurrentItem(mDatas.size() / 2, true);

    }

    @Override
    public void onError(String error) {
        super.onError(error);
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        mPrestener.getCollect(mContext, page);

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
