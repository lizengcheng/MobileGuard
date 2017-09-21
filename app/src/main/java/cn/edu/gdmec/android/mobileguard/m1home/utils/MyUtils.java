package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取版本号
 * Created by as on 2017/9/12.
 * @return 返回版本号
 */

public class MyUtils {
    public static String getVersion(Context context)
    {
        //PackageManager 可以获取清单文件中的所有信息
        PackageManager manager = context.getPackageManager();
        try {
            //getPackageName()获取到当前程序的包名
            PackageInfo packageInfo = manager.getPackageInfo(
                    context.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
