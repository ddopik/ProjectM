package com.spade.mek.ui.more;

import com.spade.mek.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public interface MorePresenter extends BasePresenter<MoreView> {
    void login();

    void logout();

    void changeLanguage(String lang);
}
