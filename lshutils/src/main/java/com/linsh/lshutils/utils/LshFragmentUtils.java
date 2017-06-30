package com.linsh.lshutils.utils;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

;

/**
 * Created by Senh Linsh on 16/12/2.
 */
public class LshFragmentUtils {

    public static void replaceFragment(android.app.Fragment fragment, int containerViewId, Activity activity) {
        if (fragment != null) {
            android.app.FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.replace(containerViewId, fragment);
            ft.commit();
        }
    }

    public static void replaceFragment(Fragment fragment, int containerViewId, AppCompatActivity activity) {
        if (fragment != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(containerViewId, fragment);
            transaction.commit();
        }
    }
}
