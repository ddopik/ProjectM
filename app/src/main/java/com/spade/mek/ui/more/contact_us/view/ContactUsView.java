package com.spade.mek.ui.more.contact_us.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.contact_us.model.ContactUsInfo;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public interface ContactUsView extends BaseView {
    void showLoading();

    void hideLoading();

    void showData(ContactUsInfo contactUsInfo);
}
