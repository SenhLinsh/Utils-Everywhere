package com.linsh.lshutils.utils;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Fragment 相关
 * </pre>
 */
public class LshFragmentUtils {

    /**
     * 创建切换 replace
     *
     * @param fragment        Fragment
     * @param containerViewId 容器 id
     * @param activity        Activity
     */
    public static void replaceFragment(android.app.Fragment fragment, int containerViewId, Activity activity) {
        if (fragment != null) {
            android.app.FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.replace(containerViewId, fragment);
            ft.commit();
        }
    }

    /**
     * 创建切换 replace
     *
     * @param fragment        Fragment
     * @param containerViewId 容器 id
     * @param activity        Activity
     */
    public static void replaceFragment(Fragment fragment, int containerViewId, AppCompatActivity activity) {
        if (fragment != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(containerViewId, fragment);
            transaction.commit();
        }
    }
}
