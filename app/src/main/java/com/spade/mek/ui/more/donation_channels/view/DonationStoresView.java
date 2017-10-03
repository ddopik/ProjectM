package com.spade.mek.ui.more.donation_channels.view;

import android.location.Location;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.donation_channels.model.City;
import com.spade.mek.ui.more.donation_channels.model.Store;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public interface DonationStoresView extends BaseView {

    void showStores(List<Store> storeList);

    void showCitiesAndAreas(List<City> cities);

    void setUserLocation(Location userLocation);

    void showSortedList(List<Store> storeList);

    void showLoading();

    void hideLoading();
}
