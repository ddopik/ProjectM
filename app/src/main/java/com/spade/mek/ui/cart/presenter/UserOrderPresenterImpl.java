package com.spade.mek.ui.cart.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.model.Order;
import com.spade.mek.ui.cart.model.OrderItems;
import com.spade.mek.ui.cart.view.PaymentActivity;
import com.spade.mek.ui.cart.view.UserDataFragment;
import com.spade.mek.ui.cart.view.UserDataView;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.login.User;
import com.spade.mek.utils.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class UserOrderPresenterImpl implements UserOrderPresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private UserDataView userDataView;
    private Order order;
    private int paymentType;
    private String orderId;
    private double zakatAmount;
    private int checkOutType;

    public UserOrderPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(UserDataView view) {
        userDataView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void makeOrder(String typeOfDonation, int paymentType) {
        userDataView.showLoading();
        this.paymentType = paymentType;
        this.checkOutType = UserDataFragment.EXTRA_PAY_FOR_PRODUCTS;
        order = new Order();
        order.setTypeOfDonation(typeOfDonation);
        new GetUserAsyncTask().execute();
    }

    @Override
    public void donateZakat(double moneyAmount) {
        this.zakatAmount = moneyAmount;
        this.checkOutType = UserDataFragment.EXTRA_DONATE_ZAKAT;
        order = new Order();
        new GetUserAsyncTask().execute();
    }

    @Override
    public double getOrderTotalCost(String userId) {
        return realmDbHelper.getTotalCost(userId);
    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void updateUserData(String firstName, String lastName, String phoneNumber,
                               String emailAddress, String address, String userId) {
        realmDbHelper.updateUserData(firstName, lastName, phoneNumber,
                emailAddress, address, userId);
    }

    @Override
    public void finishPaymentStatus(int paymentStatus) {
        if (paymentStatus == PaymentActivity.PAYMENT_SUCCESS && checkOutType == UserDataFragment.EXTRA_PAY_FOR_PRODUCTS) {
            realmDbHelper.deleteAllCartItems(PrefUtils.getUserId(mContext));
        }
        userDataView.showLoading();
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("status", paymentStatus);
            requestJsonObject.put("order_id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiHelper.changeStatus(requestJsonObject, new ApiHelper.ChangePaymentStatus() {
            @Override
            public void onStatusCHangedSuccess() {
                realmDbHelper.updateOrderStatus(orderId, true);
                userDataView.hideLoading();
                userDataView.navigateToConfirmationScreen();
                userDataView.finish();
            }

            @Override
            public void onStatusChangedFailed() {
                realmDbHelper.updateOrderStatus(orderId, false);
                userDataView.hideLoading();
                userDataView.finish();
            }
        });
    }


    private void checkoutOrder(JSONObject requestJson) {
        if (paymentType == UserDataFragment.ONLINE_PAYMENT_TYPE) {
            userDataView.showLoading();
            ApiHelper.createOnlinePaymentOrder(requestJson, PrefUtils.getUserToken(mContext))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(paymentResponse -> {
                        userDataView.hideLoading();
                        if (paymentResponse != null && paymentResponse.isSuccess()) {
                            realmDbHelper.saveOrderDone(paymentResponse.getPaymentResponseData().getOrderId());
                            orderId = paymentResponse.getPaymentResponseData().getOrderId();
                            userDataView.navigateToPayment(paymentResponse.getPaymentResponseData().getPaymentUrl());
                        }
                    }, throwable -> {
                        userDataView.hideLoading();
                        if (throwable != null)
                            userDataView.onError(throwable.getMessage());
                    });

        } else if (paymentType == UserDataFragment.CASH_ON_DELIVERY) {
            userDataView.showLoading();
            ApiHelper.createOrder(requestJson, new ApiHelper.CreateOrderCallbacks() {
                @Override
                public void onOrderCreatedSuccess(boolean isSuccess) {
                    userDataView.hideLoading();
                    if (isSuccess) {
                        if (checkOutType == UserDataFragment.EXTRA_PAY_FOR_PRODUCTS)
                            realmDbHelper.deleteAllCartItems(PrefUtils.getUserId(mContext));
                        userDataView.navigateToConfirmationScreen();
                        userDataView.finish();
                    } else {
                        userDataView.onError(mContext.getString(R.string.something_wrong));
                    }
                }

                @Override
                public void onOrderCreatedFailed(String error) {
                    userDataView.hideLoading();
                    userDataView.onError(error);
                }
            });
        }
    }


    private class GetProductsAsyncTask extends AsyncTask<Void, Void, List<OrderItems>> {
        @Override
        protected List<OrderItems> doInBackground(Void... params) {
            List<OrderItems> orderItemsList = new ArrayList<>();
            List<CartItem> cartItemList =
                    realmDbHelper.getCartItems(PrefUtils.getUserId(mContext));
            for (CartItem cartItem : cartItemList) {
                OrderItems orderItems = new OrderItems();
                orderItems.setProduct_id(String.valueOf(cartItem.getItemId()));
                if (cartItem.getItemType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
                    orderItems.setQuantity(String.valueOf(cartItem.getMoneyAmount()));
                } else {
                    orderItems.setQuantity(String.valueOf(cartItem.getAmount()));
                }
                orderItemsList.add(orderItems);
            }
            return orderItemsList;
        }

        @Override
        protected void onPostExecute(List<OrderItems> orderItemsList) {
            super.onPostExecute(orderItemsList);
            order.setOrderItems(orderItemsList);
            new CreateJsonRequestObject().execute();
        }
    }

    private class GetUserAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            User user =
                    getUser(PrefUtils.getUserId(mContext));
            order.setFirstName(user.getFirstName());
            order.setLastName(user.getLastName());
            order.setAddress(user.getUserAddress());
            order.setEmailAddress(user.getUserEmail());
            order.setPhoneNumber(user.getUserPhone());

            return null;
        }

        @Override
        protected void onPostExecute(Void orderItemsList) {
            super.onPostExecute(orderItemsList);
            if (checkOutType == UserDataFragment.EXTRA_DONATE_ZAKAT) {
                new CreateZakatJsonRequestObject().execute();
            } else {
                new GetProductsAsyncTask().execute();
            }
        }
    }

    private class CreateJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONArray jsonElements = new JSONArray();
            JSONObject requestJsonObject = null;
            try {

                for (OrderItems orderItems : order.getOrderItems()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("product_id", orderItems.getProduct_id());
                    jsonObject.put("quantity", orderItems.getQuantity());
                    jsonElements.put(jsonObject);
                }

                requestJsonObject = new JSONObject();
                requestJsonObject.put("first_name", order.getFirstName());
                requestJsonObject.put("last_name", order.getLastName());
                requestJsonObject.put("email", order.getEmailAddress());
                requestJsonObject.put("phone", order.getPhoneNumber());
                requestJsonObject.put("type_of_donation", order.getTypeOfDonation());
                requestJsonObject.put("address", order.getAddress());
                requestJsonObject.put("amount", getOrderTotalCost(PrefUtils.getUserId(mContext)));
                requestJsonObject.put("products", jsonElements);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            checkoutOrder(jsonObject);
        }
    }

    private class CreateZakatJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONArray jsonElements = new JSONArray();
            JSONObject requestJsonObject = null;
            try {
                requestJsonObject = new JSONObject();
                requestJsonObject.put("first_name", order.getFirstName());
                requestJsonObject.put("last_name", order.getLastName());
                requestJsonObject.put("email", order.getEmailAddress());
                requestJsonObject.put("phone", order.getPhoneNumber());
                requestJsonObject.put("type_of_donation", order.getTypeOfDonation());
                requestJsonObject.put("address", order.getAddress());
                requestJsonObject.put("amount", String.valueOf(zakatAmount));
                requestJsonObject.put("products", jsonElements);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            checkoutOrder(jsonObject);
        }
    }
}
