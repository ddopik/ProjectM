package com.spade.mek.ui.more.donation_channels.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.donation_channels.view.DonationBanksView;
import com.spade.mek.ui.more.donation_channels.view.DonationStoresView;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationBanksPresenter extends BasePresenter<DonationBanksView> {
    void getDonationBanks(String appLang);
}
