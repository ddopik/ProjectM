package com.spade.mek.ui.cart.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.cart.view.UserDataView;
import com.spade.mek.ui.login.User;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface UserOrderPresenter extends BasePresenter<UserDataView> {
    void makeOrder(String typeOfDonation, int type, int donationWay, int donationRegion);

    void donateZakat(double moneyAmount, String typeOfDonation, int type, int donationWay, int donationRegion);

    double getOrderTotalCost(String userId);

    User getUser(String userId);

    void updateUserData(String firstName, String lastName, String phoneNumber, String emailAddress, String address, String userId);

    void finishPaymentStatus(int paymentStatus);
}
