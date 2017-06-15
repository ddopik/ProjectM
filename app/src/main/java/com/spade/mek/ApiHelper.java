package com.spade.mek;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.mek.ui.home.causes.LatestCausesResponse;
import com.spade.mek.ui.home.products.LatestProductsResponse;

import io.reactivex.Observable;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class ApiHelper {

    private static final String BASE_URL = "http://dev.spade.studio/mek-apis/public/api/v1/{lang}";
    private static final String LATEST_PRODUCTS_URL = BASE_URL + "/products/latest";
    private static final String LATEST_CAUSES_URL = BASE_URL + "/causes/latest";

    private static final String LANG_PATH_PARAMETER = "lang";

    public static Observable<LatestProductsResponse> getLatestProducts(String lang) {
        return Rx2AndroidNetworking.get(LATEST_PRODUCTS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .build()
                .getObjectObservable(LatestProductsResponse.class);
    }

    public static Observable<LatestCausesResponse> getLatestCauses(String lang) {
        return Rx2AndroidNetworking.get(LATEST_PRODUCTS_URL)
                .addPathParameter(LANG_PATH_PARAMETER, lang)
                .build()
                .getObjectObservable(LatestCausesResponse.class);
    }


}
