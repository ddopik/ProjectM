package com.spade.mek.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.causes.CausesFragment;
import com.spade.mek.ui.products.view.ProductsFragment;
import com.spade.mek.utils.NavigationManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AHBottomNavigation.OnTabSelectedListener, HomeFragment.HomeActions {

    private AHBottomNavigation ahBottomNavigation;
    private static final int HOME_POSITION = 0;
    private static final int CAUSES_POSITION = 1;
    private static final int PRODUCTS_POSITION = 2;
    private static final int MORE_POSITION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        init();
    }

    @Override
    protected void addFragment() {

    }


    @Override
    protected void addFragment(String title, Fragment fragment) {
        setTitle(title);
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, fragment, this, fragment.getClass().getSimpleName());
    }

    private void init() {
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottomNavigation);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setAccentColor(Color.parseColor("#E7891E"));
        ahBottomNavigation.setInactiveColor(Color.parseColor("#01513e"));
        ahBottomNavigation.setForceTint(true);
        ahBottomNavigation.addItems(getNavigationItems());
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setCurrentItem(HOME_POSITION);
        openHomeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounter();
    }

    private List<AHBottomNavigationItem> getNavigationItems() {
        AHBottomNavigationItem homeItem = new AHBottomNavigationItem(getString(R.string.title_home), R.drawable.ic_home);
        AHBottomNavigationItem causesItem = new AHBottomNavigationItem(getString(R.string.title_causes), R.drawable.ic_causes);
        AHBottomNavigationItem productsItem = new AHBottomNavigationItem(getString(R.string.title_products), R.drawable.ic_products);
        AHBottomNavigationItem moreItem = new AHBottomNavigationItem(getString(R.string.title_more), R.drawable.ic_more);

        List<AHBottomNavigationItem> ahBottomNavigationItems = new ArrayList<>();
        ahBottomNavigationItems.add(homeItem);
        ahBottomNavigationItems.add(causesItem);
        ahBottomNavigationItems.add(productsItem);
        ahBottomNavigationItems.add(moreItem);

        return ahBottomNavigationItems;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        updateCounter();
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (wasSelected) {
            return false;
        }
        switch (position) {
            case HOME_POSITION:
                openHomeFragment();
                return true;
            case CAUSES_POSITION:
                openCausesFragment();
                return true;
            case PRODUCTS_POSITION:
                openProductsFragment();
                return true;
        }
        return true;
    }

    private void openHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setHomeActions(this);
        addFragment(getString(R.string.title_home), homeFragment);
    }

    private void openProductsFragment() {
        ProductsFragment productsFragment = new ProductsFragment();
        addFragment(getString(R.string.title_products), productsFragment);
    }


    private void openMoreFragment() {
        setTitle(R.string.title_more);

    }

    private void openCausesFragment() {
        CausesFragment causesFragment = new CausesFragment();
        addFragment(getString(R.string.title_causes), causesFragment);
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onCheckAllProductsClicked() {
        ahBottomNavigation.setCurrentItem(PRODUCTS_POSITION, true);
    }

    @Override
    public void onCheckAllCausesClicked() {
        ahBottomNavigation.setCurrentItem(CAUSES_POSITION, true);
    }
}
