package com.spade.mek.ui.more.volunteering.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.cart.view.UserDataView;
import com.spade.mek.ui.login.User;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface VolunteerPresenter extends BasePresenter<UserDataView> {

    User getUser(String userId);

    void volunteer(String firstName, String lastName, String phoneNumber, String emailAddress, String address, String userId);

}
