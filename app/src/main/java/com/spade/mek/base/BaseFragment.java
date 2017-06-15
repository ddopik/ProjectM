package com.spade.mek.base;

import android.support.v4.app.Fragment;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        initPresenter();
    }

    protected abstract void initPresenter();

    protected abstract void initViews();
}
