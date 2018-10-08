package com.sjjd.wyl.playandroid.view.activities.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.base.App;
import com.sjjd.wyl.playandroid.base.BaseActivity;
import com.sjjd.wyl.playandroid.bean.UserBean;
import com.sjjd.wyl.playandroid.presenter.IMainPrestener;
import com.sjjd.wyl.playandroid.presenter.IPrestenerMain;
import com.sjjd.wyl.playandroid.view.fragments.CategoryFragment;
import com.sjjd.wyl.playandroid.view.fragments.FavouriteFragment;
import com.sjjd.wyl.playandroid.view.fragments.MainFragment;
import com.sjjd.wyl.playandroid.view.fragments.PersonFragment;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity<UserBean> implements IMainView<UserBean> {

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    HashMap<String, Fragment> fragments;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.imgAvatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.tvNick)
    TextView mTvNick;
    @BindView(R.id.tvTool)
    TextView mTvTool;
    @BindView(R.id.tvCollect)
    TextView mTvCollect;
    @BindView(R.id.tvSignIn)
    TextView mTvSignIn;
    @BindView(R.id.etNick)
    EditText mEtNick;
    @BindView(R.id.etPsw)
    EditText mEtPsw;
    @BindView(R.id.etPswCom)
    EditText mEtPswCom;
    @BindView(R.id.llPswConfirm)
    LinearLayout mLlPswConfirm;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.tvRegister)
    TextView mTvRegister;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    @BindView(R.id.llLoginRegister)
    LinearLayout mLlLoginRegister;
    @BindView(R.id.tvLogin)
    TextView mTvLogin;

    Animation mMaxAnimation;
    Animation mMinAnimation;
    boolean userLogined = false;

    IPrestenerMain mPrestenerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPrestenerMain = new IMainPrestener(this);

        SharedPreferences mUser = getSharedPreferences("User", MODE_PRIVATE);
        userLogined = mUser.getBoolean("login", false);
        App.getInstance().logined = userLogined;

        if (userLogined) {
            mTvSignIn.setText("退出登录");
            String nick = mUser.getString("nick", null);
            if (nick != null) {
                mTvNick.setText(nick);
            }
        }
        fragments = new HashMap<>();
        mMaxAnimation = AnimationUtils.loadAnimation(this,
                R.anim.search_max);
        mMaxAnimation.setFillAfter(true);
        mMinAnimation = AnimationUtils.loadAnimation(this,
                R.anim.search_min);
        mMinAnimation.setFillAfter(true);

        setListener();

        initUI();
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.e(TAG, "onDrawerClosed: ");

            }

        });


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

    private void initUI() {

        if (userLogined) {
            mTvSignIn.setText("退出登录");
        } else {
            mTvSignIn.setText("登录/注册");
        }

        loingShowing = false;

        mTvRegister.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.VISIBLE);
        mLlLoginRegister.setVisibility(View.GONE);
        mLlLoginRegister.startAnimation(mMinAnimation);
        mTvLogin.setVisibility(View.GONE);
        mLlPswConfirm.setVisibility(View.GONE);
        mBtnRegister.setVisibility(View.GONE);

        mEtNick.setText("");
        mEtPsw.setText("");
        mEtPswCom.setText("");
    }


    private void hideFragments(FragmentTransaction transaction) {
        Iterator<Fragment> mIterator = fragments.values().iterator();
        while (mIterator.hasNext()) {
            transaction.hide(mIterator.next());
        }
    }

    boolean loingShowing = false;

    @OnClick({R.id.imgAvatar, R.id.tvNick, R.id.tvTool, R.id.tvCollect, R.id.tvSignIn,
            R.id.btnLogin, R.id.tvRegister, R.id.btnRegister, R.id.tvLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgAvatar:
                break;
            case R.id.tvNick:
                break;
            case R.id.tvTool:
                break;
            case R.id.tvCollect:
                break;
            case R.id.tvSignIn:
                if (userLogined) {
                    userLogined = false;
                    App.getInstance().logined = false;
                    SharedPreferences mUser = getSharedPreferences("User", MODE_PRIVATE);
                    mUser.edit().putBoolean("login", false).apply();
                    mUser.edit().putString("nick", null).apply();
                    mTvNick.setText("Play Android");
                    mTvSignIn.setText("登录/注册");
                    loingShowing = false;

                } else if (loingShowing) {
                    mLlLoginRegister.setVisibility(View.GONE);
                    mLlLoginRegister.startAnimation(mMinAnimation);
                    loingShowing = false;
                } else {
                    mLlLoginRegister.setVisibility(View.VISIBLE);
                    mLlLoginRegister.startAnimation(mMaxAnimation);
                    loingShowing = true;
                }
                break;
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvRegister:

                mTvRegister.setVisibility(View.GONE);
                mBtnLogin.setVisibility(View.GONE);
                mTvLogin.setVisibility(View.VISIBLE);
                mBtnRegister.setVisibility(View.VISIBLE);
                mLlPswConfirm.setVisibility(View.VISIBLE);
                break;
            case R.id.btnRegister:
                register();
                break;
            case R.id.tvLogin:
                mTvRegister.setVisibility(View.VISIBLE);
                mBtnLogin.setVisibility(View.VISIBLE);
                mTvLogin.setVisibility(View.GONE);
                mBtnRegister.setVisibility(View.GONE);
                mLlPswConfirm.setVisibility(View.GONE);
                break;
        }
    }

    private void register() {
        final String nick = mEtNick.getText().toString();
        String psw = mEtPsw.getText().toString();
        String repsw = mEtPswCom.getText().toString();
        if (TextUtils.isEmpty(nick)) {
            mEtNick.setError("请输入用户名！");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            mEtPsw.setError("请输入密码！");
            return;
        }
        if (TextUtils.isEmpty(repsw)) {
            mEtPsw.setError("请确认密码！");
            return;
        }

        mPrestenerMain.register(mContext, nick, psw, repsw);

    }

    @Override
    public void onError(String error) {
        super.onError(error);
        Toast.makeText(mContext, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(UserBean result) {
        super.onSuccess(result);
        if (result.getData() != null) {
            userLogined = true;
            App.getInstance().logined = true;
            SharedPreferences mUser = getSharedPreferences("User", MODE_PRIVATE);
            mUser.edit().putBoolean("login", true).apply();
            mUser.edit().putString("nick", result.getData().getUsername()).apply();
            mEtNick.setText("");
            mEtPsw.setText("");
            mEtPswCom.setText("");
            mLlLoginRegister.startAnimation(mMinAnimation);
            mTvSignIn.setText("退出登录");
            mTvNick.setText(result.getData().getUsername());
            mLlLoginRegister.setVisibility(View.GONE);
        }
    }

    private void login() {
        final String nick = mEtNick.getText().toString();
        String psw = mEtPsw.getText().toString();

        if (TextUtils.isEmpty(nick)) {
            mEtNick.setError("请输入用户名！");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            mEtPsw.setError("请输入密码！");
            return;
        }

        mPrestenerMain.login(mContext, nick, psw);

    }

}
