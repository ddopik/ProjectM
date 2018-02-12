package com.spade.mek.ui.more.about.view.tabs;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.about.model.AboutUsDataResponse;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public interface AboutMisrFragmentView extends BaseView {

    void showAboutMisr(AboutUsDataResponse aboutUsDataResponse);

    void hideAboutMisr();

    void showAboutMisrLoading();

    void hideAboutMisrLoading();
}
