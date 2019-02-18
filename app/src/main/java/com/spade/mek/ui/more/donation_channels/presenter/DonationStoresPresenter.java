package com.spade.mek.ui.more.donation_channels.presenter;

import android.app.Activity;
import android.location.Location;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.donation_channels.model.Store;
import com.spade.mek.ui.more.donation_channels.view.DonationStoresView;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationStoresPresenter extends BasePresenter<DonationStoresView> {
    void getDonationStores(String appLang);

   void sortStoresAscending(List<Store> storeList, Location userLocation);

    void requestLocationPermission(Activity activity);

}
