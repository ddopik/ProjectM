package com.spade.mek.utils;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public enum LoginProviders {
    NONE(0),
    FACEBOOK(10),
    GOOGLE(20);

    private int loginProviderCode;

    LoginProviders(int loginProviderCode) {
        this.loginProviderCode = loginProviderCode;
    }

    public int getLoginProviderCode() {
        return loginProviderCode;
    }
}
