package com.spade.mek.ui.more.profile.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.profile.model.Payment;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public interface ProfilePaymentView extends BaseView {
    void showPaymentData(List<Payment> payments);

    void showLoading();

    void hideLoading();
}
