package com.spade.mek.realm;

import com.spade.sociallogin.SocialUser;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public interface RealmDbHelper {
    void saveUser(SocialUser socialUser);
}
