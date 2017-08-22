package com.spade.mek.ui.more.regular_products.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.regular_products.view.SubscribeView;
import com.spade.mek.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 8/22/17.
 */

public class SubscribePresenterImpl implements SubscribePresenter {

    private SubscribeView subscribeView;
    private Context mContext;
    private Products product;
    private double totalAmount;
    private int quantity, duration;

    public SubscribePresenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setView(SubscribeView view) {
        subscribeView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void subscribeToProduct(Products products, double amount, int quantity, int duration) {
        subscribeView.showLoading();
        this.product = products;
        this.quantity = quantity;
        this.totalAmount = amount;
        this.duration = duration;
        new CreateJsonRequestObject().execute();
    }

    private void subscribe(JSONObject jsonObject) {
        ApiHelper.subscribeToProduct(jsonObject, PrefUtils.getUserToken(mContext), new ApiHelper.SubscriptionCallBacks() {
            @Override
            public void onSubscribeSuccess() {
                subscribeView.hideLoading();
            }

            @Override
            public void onSubscriptionFailed() {
                subscribeView.hideLoading();
            }
        });
    }

    private class CreateJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject requestJsonObject = null;
            try {
                requestJsonObject = new JSONObject();
                requestJsonObject.put("product_id", product.getProductId());
                requestJsonObject.put("quantity", quantity);
                requestJsonObject.put("amount", totalAmount);
                requestJsonObject.put("duration", duration);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            subscribe(jsonObject);
        }
    }
}
