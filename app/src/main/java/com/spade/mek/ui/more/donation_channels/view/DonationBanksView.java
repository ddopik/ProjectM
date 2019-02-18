package com.spade.mek.ui.more.donation_channels.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.donation_channels.model.Bank;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationBanksView extends BaseView {

    void showBanks(List<Bank> bankList);

//    void showCitiesAndAreas(List<City> cities);

    void showLoading();

    void hideLoading();
}
