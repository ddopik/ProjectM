package com.spade.mek.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class MoreFragment extends BaseFragment implements MoreView {

    private View moreView;
    private boolean isLoggedIn = false;
    private MorePresenter morePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_more, container, false);
        initViews();
        return moreView;
    }

    @Override
    protected void initPresenter() {
        morePresenter = new MorePresenterImpl(getContext());
    }

    @Override
    protected void initViews() {
        Button logBtn = (Button) moreView.findViewById(R.id.log_button);
        isLoggedIn = PrefUtils.getLoginProvider(getContext()) != LoginProviders.NONE.getLoginProviderCode();
        if (isLoggedIn) {
            logBtn.setText(getString(R.string.logout));
        } else {
            logBtn.setText(getString(R.string.login));
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {

    }
}
