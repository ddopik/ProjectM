package com.spade.mek.ui.cart.model;

import io.realm.RealmObject;

/**
 * Created by Ayman Abouzeid on 8/13/17.
 */

public class OrderDone extends RealmObject {

    private String orderID;
    private boolean isSynced;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
