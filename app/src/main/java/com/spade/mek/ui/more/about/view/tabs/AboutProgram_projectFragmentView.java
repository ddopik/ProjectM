package com.spade.mek.ui.more.about.view.tabs;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.about.model.AboutUsDataResponse;
import com.spade.mek.ui.more.about.model.AboutUsProjectItem;

import java.util.List;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public interface AboutProgram_projectFragmentView extends BaseView {


    void showProjectAndPrograms(AboutUsDataResponse aboutUsDataResponse);

    void hideProjectAndPrograms();

    void showProjectAndProgramsLoading();

    void hideProjectAndProgramsLoading();


}
