package com.spade.mek.realm;

import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.login.User;

import io.realm.annotations.RealmModule;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

@RealmModule(classes = {User.class, CartItem.class})
public class RealmModules {
}
