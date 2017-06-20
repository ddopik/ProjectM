package com.spade.mek.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.spade.mek.R;
import com.spade.mek.ui.causes.CausesFragment;
import com.spade.mek.ui.products.view.ProductsFragment;
import com.spade.mek.utils.NavigationManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        AHBottomNavigation ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottomNavigation);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setAccentColor(Color.parseColor("#E7891E"));
        ahBottomNavigation.setInactiveColor(Color.parseColor("#01513e"));
        ahBottomNavigation.setForceTint(true);
        ahBottomNavigation.addItems(getNavigationItems());
        ahBottomNavigation.setCurrentItem(0);
        openHomeFragment();
        ahBottomNavigation.setOnTabSelectedListener(this);
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
    public boolean onTabSelected(int position, boolean wasSelected) {
        switch (position) {
            case 0:
                openHomeFragment();
                return true;
            case 1:
                openCausesFragment();
                return true;
            case 2:
                openProductsFragment();
                return true;
        }
        return true;
    }

    private void openHomeFragment() {
        setTitle(R.string.title_home);
        HomeFragment homeFragment = new HomeFragment();
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, homeFragment, this, HomeFragment.class.getSimpleName());
    }

    private void openProductsFragment() {
        setTitle(R.string.title_products);
        ProductsFragment productsFragment = new ProductsFragment();
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, productsFragment, this, ProductsFragment.class.getSimpleName());
    }


    private void openMoreFragment() {
        setTitle(R.string.title_more);

    }

    private void openCausesFragment() {
        setTitle(R.string.title_causes);
        CausesFragment causesFragment = new CausesFragment();
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, causesFragment, this, CausesFragment.class.getSimpleName());
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
