package com.spade.mek.ui.causes;

import com.spade.mek.base.BasePresenter;

import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface CausesPresenter extends BasePresenter<CausesView> {

    void getAllCauses(String lang, int page);

    void getUrgentCases(String lang);

    void filterCauses(String lang, JSONObject jsonObject);

    void search(String searchKeyWord);

}
