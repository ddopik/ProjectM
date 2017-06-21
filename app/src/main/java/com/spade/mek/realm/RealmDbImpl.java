package com.spade.mek.realm;

import com.spade.mek.ui.login.User;
import com.spade.sociallogin.SocialUser;

import io.realm.Realm;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public class RealmDbImpl implements RealmDbHelper {
    public RealmDbImpl() {
    }

    @Override
    public void saveUser(SocialUser socialUser) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.executeTransaction(realm -> {
            User user = realm.createObject(User.class);
            user.setUserEmail(socialUser.getEmailAddress());
            user.setUserName(socialUser.getName());
            user.setUserId(socialUser.getUserId());
            user.setUserPhoto(socialUser.getUserPhoto());
        });
        realmInstance.close();
    }
}
