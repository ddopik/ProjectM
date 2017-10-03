package com.spade.mek.ui.register;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface RegisterView extends BaseView {

    void showLoading();

    void hideLoading();

    void finish();

    void navigate();

}
