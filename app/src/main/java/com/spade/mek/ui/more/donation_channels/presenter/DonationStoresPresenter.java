package com.spade.mek.ui.more.donation_channels.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.donation_channels.view.DonationStoresView;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationStoresPresenter extends BasePresenter<DonationStoresView> {
    void getDonationStores(String appLang);
}
