package com.spade.mek.network;

import android.support.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.mek.ui.cart.model.PaymentResponse;
import com.spade.mek.ui.causes.AllCausesResponse;
import com.spade.mek.ui.home.FilterCategoriesResponse;
import com.spade.mek.ui.home.causes.LatestCausesResponse;
import com.spade.mek.ui.home.products.LatestProductsResponse;
import com.spade.mek.ui.home.urgent_cases.UrgentCasesResponse;
import com.spade.mek.ui.more.contact_us.model.ContactUsDataResponse;
import com.spade.mek.ui.more.donation_channels.model.BanksResponse;
import com.spade.mek.ui.more.donation_channels.model.StoresResponse;
import com.spade.mek.ui.more.news.model.AllNewsResponse;
import com.spade.mek.ui.more.news.model.NewsDetailsResponse;
import com.spade.mek.ui.more.news.model.RelatedNewsResponse;
import com.spade.mek.ui.products.model.AllProductsResponse;
import com.spade.mek.ui.products.model.ProductDetailsResponse;
import com.spade.mek.ui.register.RegistrationResponse;
import com.spade.mek.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class ApiHelper {
    private static final String BASE_URL = "http://dev.spade.studio/mek-apis/public/api/v1/{lang}";
    private static final String BASE_POST_URL = "http://dev.spade.studio/mek-apis/public/api/v1";
    //    private static final String BASE_URL = "http://mekapi.spade.studio/api/v1/{lang}";
    private static final String LATEST_PRODUCTS_URL = BASE_URL + "/products/latest";
    private static final String LATEST_CAUSES_URL = BASE_URL + "/causes/latest";
    private static final String URGENT_CASES_URL = BASE_URL + "/products/urgent";
    private static final String ALL_PRODUCTS_URL = BASE_URL + "/products";
    private static final String REGULAR_PRODUCTS_URL = BASE_URL + "/products/regular";
    private static final String ALL_CAUSES_URL = BASE_URL + "/causes";
    private static final String ALL_NEWS_URL = BASE_URL + "/news";
    private static final String RELATED_NEWS_URL = BASE_URL + "/news/{id}/related";
    private static final String PRODUCT_DETAILS_URL = BASE_URL + "/product/{id}";
    private static final String NEWS_DETAILS_URL = BASE_URL + "/news/{id}";
    private static final String STORES_URL = BASE_URL + "/stores";
    private static final String BANKS_URL = BASE_URL + "/banks";
    private static final String CREATE_ORDER_URL = BASE_POST_URL + "/order/create";
    private static final String SEND_MESSAGE_URL = BASE_POST_URL + "/contact/store";
    private static final String CONTACT_US_INFO_URL = BASE_URL + "/contact/info";
    private static final String CATEGORIES_URL = BASE_URL + "/categories";
    private static final String FILTER_CAUSES_URL = BASE_URL + "/categories/causes";
    private static final String FILTER_PRODUCTS_URL = BASE_URL + "/categories/products";
    private static final String ONLINE_PAYMENT_CHECKOUT_URL = BASE_POST_URL + "/payment";
    private static final String REGISTER_USER_URL = BASE_POST_URL + "/register";
    private static final String LOGIN_USER_URL = BASE_POST_URL + "/login";
    private static final String SOCIAL_LOGIN_USER_URL = BASE_POST_URL + "/login/social";
    private static final String CHANGE_PAYMENT_STATUS = BASE_POST_URL + "/payment/change";
    private static final String SUBSCRIBE_URL = BASE_POST_URL + "/regular/subscribe";
    private static final String UNSUBSCRIBE_URL = BASE_POST_URL + "/regular/{id}/remove";
    private static final String LANG_PATH_PARAMETER = "lang";
    private static final String ID_PATH_PARAMETER = "id";
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String PAGE_NUMBER = "page";
    private static final String AUTH_TOKEN = "Authorization";
    private static final String BEARER = "bearer";
    private static boolean success;

    public static Observable<LatestProductsResponse> getLatestProducts(String lang) {
        return Rx2AndroidNetworking.get(LATEST_PRODUCTS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .build()
                .getObjectObservable(LatestProductsResponse.class);
    }

    public static Observable<LatestCausesResponse> getLatestCauses(String lang) {
        return Rx2AndroidNetworking.get(LATEST_CAUSES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .build()
                .getObjectObservable(LatestCausesResponse.class);
    }

    public static Observable<UrgentCasesResponse> getUrgentCases(String lang) {
        return Rx2AndroidNetworking.get(URGENT_CASES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .build()
                .getObjectObservable(UrgentCasesResponse.class);
    }

    public static Observable<StoresResponse> getStores(String appLang) {
        return Rx2AndroidNetworking.get(STORES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(StoresResponse.class);
    }

    public static Observable<BanksResponse> getBanks(String appLang) {
        return Rx2AndroidNetworking.get(BANKS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(BanksResponse.class);
    }


    public static Observable<AllProductsResponse> getAllProducts(String lang, int pageNumber) {
        return Rx2AndroidNetworking.get(ALL_PRODUCTS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .addQueryParameter(PAGE_NUMBER, String.valueOf(pageNumber))
                .build()
                .getObjectObservable(AllProductsResponse.class);
    }

    public static Observable<AllProductsResponse> getRegularProducts(String lang, int pageNumber) {
        return Rx2AndroidNetworking.get(REGULAR_PRODUCTS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .addQueryParameter(PAGE_NUMBER, String.valueOf(pageNumber))
                .build()
                .getObjectObservable(AllProductsResponse.class);
    }

    public static Observable<AllCausesResponse> getAllCauses(String lang, int pageNumber) {
        return Rx2AndroidNetworking.get(ALL_CAUSES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .addQueryParameter(PAGE_NUMBER, String.valueOf(pageNumber))
                .build()
                .getObjectObservable(AllCausesResponse.class);
    }

    public static Observable<ProductDetailsResponse> getProductDetails(String appLang, int itemId, @Nullable String userID) {
        Rx2ANRequest.GetRequestBuilder getRequestBuilder = Rx2AndroidNetworking.get(PRODUCT_DETAILS_URL);
        if (!userID.equals(PrefUtils.GUEST_USER_ID)) {
            getRequestBuilder.addQueryParameter(USER_ID_PARAMETER, userID);
        }
        return getRequestBuilder
                .addPathParameter(ID_PATH_PARAMETER, String.valueOf(itemId))
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(ProductDetailsResponse.class);
    }

    public static Observable<NewsDetailsResponse> getNewsDetails(String appLang, int itemId) {
        return Rx2AndroidNetworking.get(NEWS_DETAILS_URL)
                .addPathParameter(ID_PATH_PARAMETER, String.valueOf(itemId))
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(NewsDetailsResponse.class);
    }

    public static Observable<AllNewsResponse> getAllNews(String appLang, int pageNumber) {
        return Rx2AndroidNetworking.get(ALL_NEWS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .addPathParameter(PAGE_NUMBER, String.valueOf(pageNumber))
                .build()
                .getObjectObservable(AllNewsResponse.class);
    }

    public static Observable<RelatedNewsResponse> getRelatedNews(String appLang, int newsId) {
        return Rx2AndroidNetworking.get(RELATED_NEWS_URL)
                .addPathParameter(ID_PATH_PARAMETER, String.valueOf(newsId))
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(RelatedNewsResponse.class);
    }

    public static Observable<ContactUsDataResponse> getContactInfo(String appLang) {
        return Rx2AndroidNetworking.get(CONTACT_US_INFO_URL)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(ContactUsDataResponse.class);
    }

    public static Observable<FilterCategoriesResponse> getFilterCategories(String appLang) {
        return Rx2AndroidNetworking.get(CATEGORIES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(FilterCategoriesResponse.class);
    }

    public static Observable<AllProductsResponse> filterProducts(JSONObject filterIds, String appLang) {
        return Rx2AndroidNetworking.post(FILTER_PRODUCTS_URL)
                .addJSONObjectBody(filterIds)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(AllProductsResponse.class);
    }

    public static Observable<AllCausesResponse> filterCauses(JSONObject filterIds, String appLang) {
        return Rx2AndroidNetworking.post(FILTER_CAUSES_URL)
                .addJSONObjectBody(filterIds)
                .addPathParameter(LANG_PATH_PARAMETER, appLang)
                .build()
                .getObjectObservable(AllCausesResponse.class);
    }

    public static void sendMessage(JSONObject requestJson, SendMessageCallBacks SendMessageCallBacks) {
        AndroidNetworking.post(SEND_MESSAGE_URL)
                .addJSONObjectBody(requestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SendMessageCallBacks.onMessageSent(response.getBoolean("success"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        SendMessageCallBacks.onMessageSentFailed(anError.getMessage());

                    }
                });
    }

    public static boolean createOrder(JSONObject requestJson, CreateOrderCallbacks createOrderCallbacks) {
        success = false;
        AndroidNetworking.post(CREATE_ORDER_URL)
                .addJSONObjectBody(requestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            success = response.getBoolean("success");
                            createOrderCallbacks.onOrderCreatedSuccess(success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        createOrderCallbacks.onOrderCreatedFailed(anError.getMessage());

                    }
                });
        return success;
    }

    public static boolean changeStatus(JSONObject requestJson, ChangePaymentStatus changePaymentStatus) {
        success = false;
        AndroidNetworking.post(CHANGE_PAYMENT_STATUS)
                .addJSONObjectBody(requestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            success = response.getBoolean("success");
                            changePaymentStatus.onStatusCHangedSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            changePaymentStatus.onStatusChangedFailed();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        changePaymentStatus.onStatusChangedFailed();
                    }
                });
        return success;
    }

    public static void subscribeToProduct(JSONObject requestJson, String userToken, SubscriptionCallBacks subscriptionCallBacks) {
        AndroidNetworking.post(SUBSCRIBE_URL)
                .addJSONObjectBody(requestJson)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            success = response.getBoolean("success");
                            if (success) {
                                subscriptionCallBacks.onSubscribeSuccess();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            subscriptionCallBacks.onSubscriptionFailed();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        subscriptionCallBacks.onSubscriptionFailed();
                    }
                });
    }

    public static void unSubscribeFromProduct(String productId, String userToken, UnSubscriptionCallBacks unSubscriptionCallBacks) {
        AndroidNetworking.get(UNSUBSCRIBE_URL)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .addPathParameter(ID_PATH_PARAMETER, productId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            success = response.getBoolean("success");
                            if (success) {
                                unSubscriptionCallBacks.onUnSubscribeSuccess();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            unSubscriptionCallBacks.onUnSubscriptionFailed();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        unSubscriptionCallBacks.onUnSubscriptionFailed();
                    }
                });
    }

    public static Observable<RegistrationResponse> registerUser(JSONObject registerObject) {
        return Rx2AndroidNetworking.post(REGISTER_USER_URL)
                .addJSONObjectBody(registerObject)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<RegistrationResponse> loginUser(JSONObject loginObject) {
        return Rx2AndroidNetworking.post(LOGIN_USER_URL)
                .addJSONObjectBody(loginObject)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<PaymentResponse> createOnlinePaymentOrder(JSONObject requestJson, String userToken) {
        return Rx2AndroidNetworking.post(ONLINE_PAYMENT_CHECKOUT_URL)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .addJSONObjectBody(requestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PaymentResponse.class);
    }

    public static Observable<RegistrationResponse> socialLoginUSer(JSONObject jsonObject) {
        return Rx2AndroidNetworking.post(SOCIAL_LOGIN_USER_URL)
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public interface CreateOrderCallbacks {
        void onOrderCreatedSuccess(boolean isSuccess);

        void onOrderCreatedFailed(String error);

    }

    public interface ChangePaymentStatus {
        void onStatusCHangedSuccess();

        void onStatusChangedFailed();
    }

    public interface SendMessageCallBacks {
        void onMessageSent(boolean isSuccess);

        void onMessageSentFailed(String error);
    }

    public interface SubscriptionCallBacks {
        void onSubscribeSuccess();

        void onSubscriptionFailed();
    }

    public interface UnSubscriptionCallBacks {
        void onUnSubscribeSuccess();

        void onUnSubscriptionFailed();
    }
}
