package com.sjjd.wyl.playandroid.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.sjjd.wyl.playandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.llRoot)
    LinearLayout mLlRoot;
    AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        if (url == null || url.length() < 1)
            finish();

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLlRoot, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null && !mAgentWeb.back()) {
            this.finish();
        }
    }
}
