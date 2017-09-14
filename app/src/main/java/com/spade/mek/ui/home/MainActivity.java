package com.spade.mek.ui.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.causes.CausesFragment;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.search.SearchActivity;
import com.spade.mek.ui.home.urgent_cases.FilterCategoriesAdapter;
import com.spade.mek.ui.more.MoreFragment;
import com.spade.mek.ui.more.news.view.NewsDetailsActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.ui.products.view.ProductsFragment;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.NavigationManager;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements AHBottomNavigation.OnTabSelectedListener,
        HomeFragment.HomeActions, HomeFragment.CartAction,
        ProductsFragment.CartAction, CausesFragment.CartAction, FilterCategoriesAdapter.OnFilterCategoryClicked {

    private AHBottomNavigation ahBottomNavigation;
    private static final int HOME_POSITION = 0;
    private static final int CAUSES_POSITION = 1;
    private static final int PRODUCTS_POSITION = 2;
    private static final int SEARCH_POSITION = 3;
    private static final int MORE_POSITION = 4;
    private LinearLayout filterViewLayout;
    private TextView selectedFiltersText;
    private List<FilterCategory> filterCategories;
    private FilterCategoriesAdapter filterCategoriesAdapter;
    private int height;
    private Map<Integer, String> selectedFilters;
    private ImageView overlayImage;
    private ArrayList<String> selectedFiltersIds;
    private FloatingActionButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        init();
        if (getIntent() != null && getIntent().getType() != null && getIntent().getType().equals(MekApplication.TYPE_NOTIFICATION)) {
            handleIntent(getIntent());
        }
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
        ImageView submitFilter = findViewById(R.id.submit_filter);
        TextView clearFilters = findViewById(R.id.clear_filters);

        RecyclerView filterCategoriesRecycler = findViewById(R.id.filter_categories);
        RelativeLayout parentLayout = findViewById(R.id.container);
        filterButton = findViewById(R.id.filter_btn);
        selectedFiltersText = findViewById(R.id.selected_filter_text);
        filterViewLayout = findViewById(R.id.filter_view_layout);
        overlayImage = findViewById(R.id.overlay_image);

        clearFilters.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ahBottomNavigation = findViewById(R.id.bottomNavigation);
        ahBottomNavigation.setTitleTypeface(Typeface.createFromAsset(getAssets(), "fonts/bahij_semi_bold.ttf"));
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setAccentColor(Color.parseColor("#E7891E"));
        ahBottomNavigation.setInactiveColor(Color.parseColor("#01513e"));
        ahBottomNavigation.setForceTint(true);
        ahBottomNavigation.addItems(getNavigationItems());
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setCurrentItem(HOME_POSITION);

        ViewCompat.setElevation(filterViewLayout, getResources().getDimension(R.dimen.bottom_navigation_elevation));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        filterCategories = new ArrayList<>();
        selectedFiltersIds = new ArrayList<>();
        selectedFilters = new HashMap<>();
        filterCategoriesAdapter = new FilterCategoriesAdapter(filterCategories, this);
        filterCategoriesAdapter.setOnFilterCategoryClicked(this);

        filterCategoriesRecycler.setLayoutManager(layoutManager);
        filterCategoriesRecycler.setAdapter(filterCategoriesAdapter);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;


        filterButton.setOnClickListener(v -> showFilters());
        overlayImage.setOnClickListener(v -> hideFilters());
        submitFilter.setOnClickListener(v -> {
            hideFilters();
            if (!selectedFiltersIds.isEmpty()) {
                filter(selectedFiltersIds);
            }
        });
        clearFilters.setOnClickListener(v -> clearFilters());
        openHomeFragment();
        getFilterCategories();
        overrideFonts(this, parentLayout);
    }

    private void clearFilters() {
        selectedFiltersIds.clear();
        selectedFilters.clear();
        filterCategoriesAdapter.clearFilters();
        setFiltersText();
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
        AHBottomNavigationItem searchItem = new AHBottomNavigationItem(getString(R.string.search), R.drawable.ic_search_white);
        AHBottomNavigationItem moreItem = new AHBottomNavigationItem(getString(R.string.title_more), R.drawable.ic_more);

        List<AHBottomNavigationItem> ahBottomNavigationItems = new ArrayList<>();
        ahBottomNavigationItems.add(homeItem);
        ahBottomNavigationItems.add(causesItem);
        ahBottomNavigationItems.add(productsItem);
        ahBottomNavigationItems.add(searchItem);
        ahBottomNavigationItems.add(moreItem);

        return ahBottomNavigationItems;
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
            case MORE_POSITION:
                openMoreFragment();
                return true;
            case SEARCH_POSITION:
                openSearchActivity();
                return false;
        }
        return true;
    }

    private void openHomeFragment() {
        filterButton.setVisibility(View.GONE);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setHomeActions(this);
        homeFragment.setCartAction(this);
        addFragment(getString(R.string.title_home), homeFragment);
    }

    private void openProductsFragment() {
        filterButton.setVisibility(View.VISIBLE);
        clearFilters();
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setCartAction(this);
        addFragment(getString(R.string.title_products), productsFragment);
    }


    private void openMoreFragment() {
        filterButton.setVisibility(View.GONE);
        MoreFragment moreFragment = new MoreFragment();
        addFragment(getString(R.string.title_more), moreFragment);
    }

    private void openCausesFragment() {
        filterButton.setVisibility(View.VISIBLE);
        clearFilters();
        CausesFragment causesFragment = new CausesFragment();
        causesFragment.setCartAction(this);
        addFragment(getString(R.string.title_causes), causesFragment);
    }

    private void openSearchActivity() {
        startActivity(SearchActivity.getLaunchIntent(this));
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private void filter(ArrayList<String> filterIds) {
        if (NavigationManager.getCurrentFragment() instanceof ProductsFragment) {
            ProductsFragment.filterProducts(this, filterIds);
        } else if (NavigationManager.getCurrentFragment() instanceof CausesFragment) {
            CausesFragment.filterCauses(this, filterIds);
        }
    }

    private void getFilterCategories() {
        ApiHelper.getFilterCategories(PrefUtils.getAppLang(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filterCategoriesResponse -> {
                    if (filterCategoriesResponse != null && filterCategoriesResponse.getFilterCategories() != null) {
                        filterCategories.addAll(filterCategoriesResponse.getFilterCategories());
                        filterCategoriesAdapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        Toast.makeText(this, ErrorUtils.getErrors(anError.getErrorBody()), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showFilters() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(filterViewLayout, "translationY", height, 0);
        animTranslate.setDuration(700);
        animTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                filterViewLayout.setVisibility(View.VISIBLE);
                overlayImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animTranslate.start();
    }

    private void hideFilters() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(filterViewLayout, "translationY", 0, height);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animTranslate);
        animatorSet.setDuration(700);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                overlayImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                filterViewLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void onCheckAllProductsClicked() {
        ahBottomNavigation.setCurrentItem(PRODUCTS_POSITION, true);
    }

    @Override
    public void onCheckAllCausesClicked() {
        ahBottomNavigation.setCurrentItem(CAUSES_POSITION, true);
    }

    @Override
    public void onItemInserted() {
        updateCounter();
    }

    @Override
    public void onFilterClicked(FilterCategory filterCategory) {
        if (selectedFilters.get(filterCategory.getCategoryId()) != null) {
            selectedFilters.remove(filterCategory.getCategoryId());
            selectedFiltersIds.remove(String.valueOf(filterCategory.getCategoryId()));
        } else {
            selectedFiltersIds.add(String.valueOf(filterCategory.getCategoryId()));
            selectedFilters.put(filterCategory.getCategoryId(), filterCategory.getCategoryName());
        }
        setFiltersText();
    }

    private void setFiltersText() {
        if (selectedFilters != null && !selectedFilters.isEmpty()) {
            List<Integer> idsList = new ArrayList<>();
            idsList.addAll(selectedFilters.keySet());
            String filterName = "";
            for (int i = 0; i < idsList.size(); i++) {
                if (i != 0 && idsList.size() > 1)
                    filterName += "<font color=#3f3f3f>  |  </font> <font color=#E7891E>" + selectedFilters.get(idsList.get(i)) + "</font>";
                else
                    filterName += "<font color=#E7891E>" + selectedFilters.get(idsList.get(i)) + "</font>";
            }
            selectedFiltersText.setText(Html.fromHtml(filterName));
        } else {
            selectedFiltersText.setText("");
        }
    }

    private void handleIntent(Intent intent) {
        String type = intent.getStringExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE);
        int id = intent.getExtras().getInt(ProductDetailsFragment.ITEM_ID);

        if (type.equals(UrgentCasesPagerAdapter.NEWS_TYPE)) {
            Intent newsIntent = NewsDetailsActivity.getLaunchIntent(this);
            newsIntent.putExtra(ProductDetailsFragment.ITEM_ID, id);
            startActivity(newsIntent);
        } else {
            Intent detailsIntent = DetailsActivity.getLaunchIntent(this);
            if (type.equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
                detailsIntent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_cause));
                detailsIntent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
            } else if (type.equals(UrgentCasesPagerAdapter.PRODUCT_TYPE)) {
                detailsIntent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_product));
                detailsIntent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
            } else if (type.equals(UrgentCasesPagerAdapter.NEWS_TYPE)) {
                detailsIntent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.regular_donations));
                detailsIntent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_REGULAR_PRODUCT);
            }
            detailsIntent.putExtra(ProductDetailsFragment.ITEM_ID, id);
            startActivity(detailsIntent);
        }
    }

}
