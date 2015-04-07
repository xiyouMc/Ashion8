package com.android.hui.ashion.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by litonghui on 2015/4/2.
 */
public class ToastUtil {
    public static void showToast(@Nullable final Context context,@Nullable final  String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

}
