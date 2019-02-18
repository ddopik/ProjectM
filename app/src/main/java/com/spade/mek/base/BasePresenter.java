
package com.spade.mek.base;

/**
 * Created by spade on 6/11/17.
 */

public interface BasePresenter<V extends BaseView> {
    void setView(V view);

    void  shareItem(String url);
}
