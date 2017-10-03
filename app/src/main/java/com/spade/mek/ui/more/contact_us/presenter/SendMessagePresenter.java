package com.spade.mek.ui.more.contact_us.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.contact_us.view.SendMessageView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface SendMessagePresenter extends BasePresenter<SendMessageView> {
    User getUser(String userId);

    void sendMessage(String userName, String city, String subject, String emailAddress, String message);
}
