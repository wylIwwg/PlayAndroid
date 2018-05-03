package com.sjjd.wyl.playandroid;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.sjjd.wyl.playandroid.fragments.CategoryFragment;
import com.sjjd.wyl.playandroid.fragments.FavouriteFragment;
import com.sjjd.wyl.playandroid.fragments.MainFragment;
import com.sjjd.wyl.playandroid.fragments.PersonFragment;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    HashMap<String, Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragments = new HashMap<>();
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
                hideFragments(mTransaction);
                switch (tabId) {
                    case R.id.tab_main:
                        if (fragments.get(MainFragment.class.getName()) == null) {

                            MainFragment mMainFragment = new MainFragment();
                            fragments.put(MainFragment.class.getName(), mMainFragment);
                            mTransaction.add(R.id.contentContainer, mMainFragment).show(mMainFragment);
                        } else {
                            mTransaction.show(fragments.get(MainFragment.class.getName()));

                        }
                        break;

                    case R.id.tab_category:
                        if (fragments.get(CategoryFragment.class.getName()) == null) {

                            CategoryFragment mCateFragment = new CategoryFragment();
                            fragments.put(CategoryFragment.class.getName(), mCateFragment);
                            mTransaction.add(R.id.contentContainer, mCateFragment).show(mCateFragment);
                        } else {
                            mTransaction.show(fragments.get(CategoryFragment.class.getName()));

                        }
                        break;
                    case R.id.tab_favorites:
                        if (fragments.get(FavouriteFragment.class.getName()) == null) {

                            FavouriteFragment mFavouriteFragment = new FavouriteFragment();
                            fragments.put(FavouriteFragment.class.getName(), mFavouriteFragment);
                            mTransaction.add(R.id.contentContainer, mFavouriteFragment).show(mFavouriteFragment);
                        } else {
                            mTransaction.show(fragments.get(FavouriteFragment.class.getName()));

                        }
                        break;
                    case R.id.tab_person:
                        if (fragments.get(PersonFragment.class.getName()) == null) {

                            PersonFragment mPersonFragment = new PersonFragment();
                            fragments.put(PersonFragment.class.getName(), mPersonFragment);
                            mTransaction.add(R.id.contentContainer, mPersonFragment).show(mPersonFragment);
                        } else {
                            mTransaction.show(fragments.get(PersonFragment.class.getName()));
                        }
                        break;
                }
                mTransaction.commitAllowingStateLoss();
            }
        });
    }

    private void hideFragments(FragmentTransaction transaction) {
        Iterator<Fragment> mIterator = fragments.values().iterator();
        while (mIterator.hasNext()) {
            transaction.hide(mIterator.next());
        }
    }


}
