package com.spade.mek.ui.causes;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.search.model.SearchResponse;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface CausesView extends BaseView {

    void showUrgentCases(List<Products> urgentCaseList);

    void showAllCauses(AllCausesResponse allCausesResponse);

    void showAFilteredCauses(AllCausesResponse allCausesResponse);

    void showUrgentCasesLoading();

    void hideUrgentCasesLoading();

    void showCausesLoading();

    void hideCausesLoading();

    void showSearchResults(SearchResponse searchResponse);


}
