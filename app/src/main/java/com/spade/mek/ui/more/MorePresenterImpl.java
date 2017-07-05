package com.spade.mek.ui.more;

import android.content.Context;

import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class MorePresenterImpl implements MorePresenter {
    private Context context;
    private RealmDbHelper realmDbHelper;
    private MoreView moreView;

    public MorePresenterImpl(Context context) {
        this.context = context;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(MoreView view) {
        this.moreView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {
        PrefUtils.setLoginProvider(context, LoginProviders.NONE.getLoginProviderCode());
        realmDbHelper.deleteUser(PrefUtils.getUserId(context));
        realmDbHelper.deleteAllCartItems(PrefUtils.getUserId(context));
        PrefUtils.setUserID(context, PrefUtils.GUEST_USER_ID);
        moreView.navigateToLoginScreen();
    }
}
