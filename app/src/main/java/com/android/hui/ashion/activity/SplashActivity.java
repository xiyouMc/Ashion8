package com.android.hui.ashion.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.hui.ashion.R;
import com.android.hui.ashion.util.VersionUtil;

/**
 * Created by litonghui on 2015/4/2.
 */
public class SplashActivity extends Activity {

    private RelativeLayout mSlashLayout;
    private TextView mVersion_txt;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        initView();
        initVersion();
        AlphaAnimation();
    }

    private void init() {
        mContext = this;
    }

    private void initView() {
        mSlashLayout = (RelativeLayout) findViewById(R.id.splash_layout);

    }

    /**
     *
     */
    private void initVersion() {
        mVersion_txt = (TextView) findViewById(R.id.version_txt);
        mVersion_txt.setText(String.valueOf(getResources().getString(R.string.version_now)+VersionUtil.versionName(mContext)));
    }


    /**
     *
     */
    private void AlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(300);
        mSlashLayout.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
