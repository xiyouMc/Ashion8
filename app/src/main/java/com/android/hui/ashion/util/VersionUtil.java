package com.android.hui.ashion.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

/**
 * Created by litonghui on 2015/4/2.
 */
public class VersionUtil {
    /**
     * 获取程序的版本信息
     *
     * @param context
     * @return
     */
    public static String versionName(@Nullable final Context context) {
        String version = null;
        try {
            PackageManager pm = context.getPackageManager();
            String pkgName = context.getPackageName();
            version = String.valueOf(pm.getPackageInfo(pkgName, PackageManager.GET_UNINSTALLED_PACKAGES).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version != null ? version : null;
    }

    /**
     * 获取程序代码的相对版本
     *
     * @param context
     * @return
     */
    public static int versionCode(@Nullable final Context context) {
        int code = 0;
        try {
            PackageManager pm = context.getPackageManager();
            String pkgName = context.getPackageName();
            code = pm.getPackageInfo(pkgName, 1).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code != 0 ? code : 0;
    }


}
