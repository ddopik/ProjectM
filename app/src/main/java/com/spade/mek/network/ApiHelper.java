package com.spade.mek.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.mek.ui.causes.AllCausesResponse;
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

    private static final String LANG_PATH_PARAMETER = "lang";
    private static final String ID_PATH_PARAMETER = "id";
    private static final String PAGE_NUMBER = "page";
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

    public static Observable<AllCausesResponse> getAllCauses(String lang, int pageNumber) {
        return Rx2AndroidNetworking.get(ALL_CAUSES_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .addQueryParameter(PAGE_NUMBER, String.valueOf(pageNumber))
                .build()
                .getObjectObservable(AllCausesResponse.class);
    }

    public static Observable<ProductDetailsResponse> getProductDetails(String appLang, int itemId) {
        return Rx2AndroidNetworking.get(PRODUCT_DETAILS_URL)
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

    public static void sendMessage(JSONObject requestJson, sendMessageCallBacks sendMessageCallBacks) {
        AndroidNetworking.post(SEND_MESSAGE_URL)
                .addJSONObjectBody(requestJson)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            sendMessageCallBacks.onMessageSent(response.getBoolean("success"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        sendMessageCallBacks.onMessageSentFailed(anError.getMessage());

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

    public interface CreateOrderCallbacks {
        void onOrderCreatedSuccess(boolean isSuccess);

        void onOrderCreatedFailed(String error);

    }

    public interface sendMessageCallBacks {
        void onMessageSent(boolean isSuccess);

        void onMessageSentFailed(String error);

    }
}
