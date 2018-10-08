package com.sjjd.wyl.playandroid.view.activities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.base.App;
import com.sjjd.wyl.playandroid.base.BaseActivity;
import com.sjjd.wyl.playandroid.bean.UserBean;
import com.sjjd.wyl.playandroid.presenter.IMainPrestener;
import com.sjjd.wyl.playandroid.presenter.IPrestenerMain;
import com.sjjd.wyl.playandroid.thread.I;
import com.sjjd.wyl.playandroid.utils.LogUtils;
import com.sjjd.wyl.playandroid.utils.SPUtils;
import com.sjjd.wyl.playandroid.view.activities.main.MainActivity;
import com.sjjd.wyl.playandroid.view.iview.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<UserBean> implements IMainView<UserBean> {

    private static final String TAG = "  LoginActivity  ";
    @BindView(R.id.imgLogo)
    ImageView mImgLogo;
    @BindView(R.id.tvAppName)
    TextView mTvAppName;
    @BindView(R.id.imgLoginUser)
    ImageView mImgLoginUser;
    @BindView(R.id.etUserName)
    EditText mEtUserName;
    @BindView(R.id.imgDeleteName)
    ImageView mImgDeleteName;
    @BindView(R.id.imgLoginPsw)
    ImageView mImgLoginPsw;
    @BindView(R.id.etUserPsw)
    EditText mEtUserPsw;
    @BindView(R.id.imgDeletePsw)
    ImageView mImgDeletePsw;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.llInput)
    LinearLayout mLlInput;
    @BindView(R.id.progressBar2)
    ProgressBar mProgressBar2;
    @BindView(R.id.llprogress)
    LinearLayout mLlprogress;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;

    IPrestenerMain mPrestenerMain;

    int waitTime = 2000;
    long startTime = 0;
    String nick, psw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPrestenerMain = new IMainPrestener(this);
        initUI();
    }

    private void initUI() {
        isStart = false;

        mLlprogress.setVisibility(View.INVISIBLE);

        mLlInput.setAlpha(1.0f);
        mLlInput.setVisibility(View.VISIBLE);

        mImgDeleteName.setVisibility(View.VISIBLE);
        mImgDeletePsw.setVisibility(View.VISIBLE);
        mImgLoginUser.setVisibility(View.VISIBLE);
        mImgLoginPsw.setVisibility(View.VISIBLE);
        mEtUserName.setVisibility(View.VISIBLE);
        mEtUserPsw.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imgDeleteName, R.id.imgDeletePsw, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgDeleteName:
                mEtUserName.setText("");
                break;
            case R.id.imgDeletePsw:
                mEtUserPsw.setText("");
                break;
            case R.id.btnLogin:
                // 计算出控件的高与宽
                login();
                break;
            case R.id.btnRegister:

                break;
        }
    }

    private void login() {
        final String nick = mEtUserName.getText().toString();
        String psw = mEtUserPsw.getText().toString();

        if (TextUtils.isEmpty(nick)) {
            mEtUserName.setError("请输入用户名！");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            mEtUserPsw.setError("请输入密码！");
            return;
        }

        mBtnLogin.setEnabled(false);
        mBtnRegister.setEnabled(false);

        startTime = System.currentTimeMillis();
        initUI();
        this.nick = nick;
        this.psw = psw;
        //  mView.setVisibility(View.INVISIBLE);
        //  mView1.setVisibility(View.INVISIBLE);
        startAnimate(mLlInput, false);



    }

    @Override
    public void onError(final String error) {
        super.onError(error);
        long t = System.currentTimeMillis() - startTime;
        if (t < waitTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "" + error, Toast.LENGTH_SHORT).show();
                    mLlInput.setVisibility(View.VISIBLE);
                    mLlprogress.setVisibility(View.INVISIBLE);
                    startAnimate(mLlInput, true);
                }
            }, waitTime - t);
        } else {
            Toast.makeText(mContext, "" + error, Toast.LENGTH_SHORT).show();
            mLlInput.setVisibility(View.VISIBLE);
            mLlprogress.setVisibility(View.INVISIBLE);
            startAnimate(mLlInput, true);

        }

        mBtnLogin.setEnabled(true);
        mBtnRegister.setEnabled(true);
    }

    @Override
    public void onSuccess(final UserBean result) {
        super.onSuccess(result);
        long t = System.currentTimeMillis() - startTime;
        if (t < waitTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    junge(result);
                }
            }, waitTime - t);
        } else {
            junge(result);

        }
        mBtnLogin.setEnabled(true);
        mBtnRegister.setEnabled(true);
    }

    private void junge(UserBean result) {
        Toast.makeText(mContext, "登录成功！", Toast.LENGTH_SHORT).show();
        if (result.getData() != null) {
            App.getInstance().logined = true;
            SPUtils.init(mContext).putDIYBoolean(I.SP.USER_LOGINED, true);
            SPUtils.init(mContext).putDIYString(I.SP.USER_NAME, result.getData().getUsername());
            mEtUserName.setText("");
            mEtUserPsw.setText("");


            Intent mIntent = new Intent(mContext, MainActivity.class);
            startActivity(mIntent);
            this.finish();

        } else {
            mLlInput.setVisibility(View.VISIBLE);
            mLlprogress.setVisibility(View.INVISIBLE);
            startAnimate(mLlInput, true);
        }
    }

    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    boolean isStart;

    private void startAnimate(final View view, boolean maxed) {
        PropertyValuesHolder animator;
        PropertyValuesHolder animator2;
        if (maxed) {
            animator = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
            animator2 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        } else {
            animator = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            animator2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        }

        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(500);
        animator3.setInterpolator(new LinearInterpolator());
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                LogUtils.e(TAG, "onAnimationUpdate: " + value);
                mImgDeleteName.setAlpha(value);
                mImgDeletePsw.setAlpha(value);
                mImgLoginUser.setAlpha(value);
                mImgLoginPsw.setAlpha(value);
                mEtUserName.setAlpha(value);
                mEtUserPsw.setAlpha(value);
                if (value == 0 && !isStart) {
                    isStart = true;
                    mLlInput.setVisibility(View.INVISIBLE);
                    mLlprogress.setVisibility(View.VISIBLE);
                    progressAnimator(mLlprogress);

                }
            }
        });
        animator3.start();
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(500);
        animator3.setInterpolator(new LinearInterpolator());
        // animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

        //注登录
        mPrestenerMain.login(mContext, nick, psw);

    }

    class JellyInterpolator extends LinearInterpolator {
        private float factor;

        public JellyInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }
}
