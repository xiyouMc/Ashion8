package com.android.hui.ashion.application;

import com.android.hui.ashion.tool.MobileTools;

/**
 * Created by litonghui on 2015/4/7.
 */
public class MainApplication extends MobileToolApp {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        MobileTools.createInstance(getApplicationContext());

    }

}
