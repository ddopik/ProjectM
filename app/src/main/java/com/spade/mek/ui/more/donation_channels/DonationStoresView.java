package com.spade.mek.ui.more.donation_channels;

import com.spade.mek.base.BaseView;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationStoresView extends BaseView {

    void showStores(List<Store> storeList);

    void showLoading();

    void hideLoading();
}
