package com.sjjd.wyl.playandroid.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sjjd.wyl.playandroid.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent mIntent=null;

        /*if (SPUtils.init(this).getDIYBoolean(I.SP.USER_LOGINED)) {
            mIntent=new Intent(this, MainActivity.class);
        }else {
            mIntent=new Intent(this, LoginActivity.class);

        }
*/
        mIntent=new Intent(this, LoginActivity.class);
        startActivity(mIntent);
    }
}
