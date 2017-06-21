package com.spade.mek.utils;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class NavigationManager {

    public static void openFragment(int containerResId, Fragment fragment, AppCompatActivity instance, String tag) {
        instance.getSupportFragmentManager().
                beginTransaction().
                replace(containerResId, fragment, tag).
                addToBackStack(tag).commit();
    }

    public static void openFragmentAsRoot(int containerResId, Fragment fragment, AppCompatActivity instance, String tag) {
        instance.getSupportFragmentManager().popBackStack();
        instance.getSupportFragmentManager().beginTransaction().
                replace(containerResId, fragment, tag).
                commit();
    }
}
