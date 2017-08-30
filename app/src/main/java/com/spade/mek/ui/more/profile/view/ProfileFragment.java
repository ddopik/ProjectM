package com.spade.mek.ui.more.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.volunteering.view.PagingAdapter;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public class ProfileFragment extends BaseFragment {
    private View view;
    private TextView userName, userEmail, userPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paging, container, false);
        initViews();
        return view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragments_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        userName = (TextView) view.findViewById(R.id.user_name);
        userEmail = (TextView) view.findViewById(R.id.user_email);
        userPhone = (TextView) view.findViewById(R.id.user_phone);

        FrameLayout userDataLayout = (FrameLayout) view.findViewById(R.id.user_data_layout);
        userDataLayout.setVisibility(View.VISIBLE);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setUserData();
    }

    private void setupViewPager(ViewPager viewPager) {
        PagingAdapter adapter = new PagingAdapter(getChildFragmentManager());
        adapter.addFragment(getPaymentHistoryFragment(), getString(R.string.payment_history));
        adapter.addFragment(getProfileRegularProductsFragment(), getString(R.string.regular_donations));

        viewPager.setAdapter(adapter);
    }

    private ProfilePaymentHistoryFragment getPaymentHistoryFragment() {
        return new ProfilePaymentHistoryFragment();
    }

    private ProfileRegularProductsFragment getProfileRegularProductsFragment() {
        return new ProfileRegularProductsFragment();
    }

    private void setUserData() {
        RealmDbHelper realmDbHelper = new RealmDbImpl();
        User user = realmDbHelper.getUser(PrefUtils.getUserId(getContext()));
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getUserEmail());
        if (user.getUserPhone() != null && !user.getUserPhone().isEmpty())
            userPhone.setText(user.getUserPhone());
        else userPhone.setVisibility(View.GONE);
    }
}
