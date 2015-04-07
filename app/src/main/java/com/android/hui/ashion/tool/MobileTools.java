package com.android.hui.ashion.tool;

import android.content.Context;

/**
 * Created by litonghui on 2015/4/7.
 */
public class MobileTools {
    private static Context mContext;


    public static Context getInstance() {
        return mContext;
    }

    public static void createInstance(final Context context) {
        mContext = context;
    }

}
