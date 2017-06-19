package com.spade.mek.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.mek.ui.home.causes.LatestCausesResponse;
import com.spade.mek.ui.home.products.LatestProductsResponse;
import com.spade.mek.ui.home.urgent_cases.UrgentCasesResponse;
import com.spade.mek.ui.products.AllProductsResponse;

import io.reactivex.Observable;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class ApiHelper {
    private static final String BASE_URL = "http://mekapi.spade.studio/api/v1/{lang}";
    private static final String LATEST_PRODUCTS_URL = BASE_URL + "/products/latest";
    private static final String LATEST_CAUSES_URL = BASE_URL + "/causes/latest";
    private static final String URGENT_CASES_URL = BASE_URL + "/products/urgent";
    private static final String ALL_PRODUCTS_URL = BASE_URL + "/products";
    private static final String LANG_PATH_PARAMETER = "lang";
    private static final String PAGE_NUMBER = "page";

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

}
