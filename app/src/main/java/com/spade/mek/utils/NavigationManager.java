package com.spade.mek.utils;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.spade.mek.base.BaseFragment;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class NavigationManager {

    private static Fragment currentFragment;

    public static void openFragment(int containerResId, Fragment fragment, AppCompatActivity instance, String tag) {
        currentFragment = fragment;
        instance.getSupportFragmentManager().
                beginTransaction().
                replace(containerResId, fragment, tag).
                addToBackStack(tag).commit();
    }

    public static void openFragmentAsRoot(int containerResId, Fragment fragment, AppCompatActivity instance, String tag) {
        currentFragment = fragment;
        instance.getSupportFragmentManager().popBackStack();
        instance.getSupportFragmentManager().beginTransaction().
                replace(containerResId, fragment, tag).
                commit();
    }

    public static Fragment getCurrentFragment() {
        return currentFragment;
    }
}
