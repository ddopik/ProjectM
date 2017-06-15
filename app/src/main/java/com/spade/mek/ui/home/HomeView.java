package com.spade.mek.ui.home;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.causes.LatestCauses;
import com.spade.mek.ui.home.products.LatestProducts;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public interface HomeView extends BaseView {

    void showLatestProducts(List<LatestProducts> latestProductsList);

    void showLatestCauses(List<LatestCauses> latestCausesList);
}
