package com.linsh.lshutils.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by Senh Linsh on 16/12/2.
 */
public class LshFragmentUtils {

    public static void replaceFragment(Fragment fragment, int containerViewId, Activity activity) {
        if (fragment != null) {
            FragmentManager fm = activity.getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(containerViewId, fragment);
            ft.commit();
        }
    }
}
