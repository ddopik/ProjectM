package com.spade.mek.ui.more.profile.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.profile.view.ProfilePaymentView;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public interface PaymentPresenter extends BasePresenter<ProfilePaymentView> {

    void getPaymentHistory(String appLang, String token);

}
