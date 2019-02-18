package com.spade.mek.ui.cart.model;

import java.io.Serializable;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class OrderItems implements Serializable{

    private String product_id;
    private String quantity;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
