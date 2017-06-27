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
    private static final String PRODUCT_DETAILS_URL = BASE_URL + "/product/{id}";
    private static final String CREATE_ORDER_URL = BASE_POST_URL + "/order/create";

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

    public static boolean createOrder(JSONObject requestJson, OnOrderCreated onOrderCreated) {
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
                            onOrderCreated.onOrderCreatedSuccess(success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        onOrderCreated.onOrderCreatedFailed(anError.getMessage());

                    }
                });
        return success;

    }

    public interface OnOrderCreated {
        void onOrderCreatedSuccess(boolean isSuccess);

        void onOrderCreatedFailed(String error);
    }

}
