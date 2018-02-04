package com.spade.mek.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.view.AddCauseToCartDialog;
import com.spade.mek.ui.cart.view.AddProductToCartDialog;
import com.spade.mek.ui.home.adapters.LatestCausesAdapter;
import com.spade.mek.ui.home.adapters.LatestProductsAdapter;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;

import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.news.model.News;
import com.spade.mek.ui.more.news.view.NewsAdapter;
import com.spade.mek.ui.more.news.view.NewsDetailsActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ConstUtil;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class HomeFragment extends BaseFragment implements HomeView, LatestProductsAdapter.OnProductClicked,
        LatestCausesAdapter.OnCauseClicked, UrgentCasesPagerAdapter.OnCaseClicked, NewsAdapter.OnNewsClicked,
        AddProductToCartDialog.AddToCart,
        AddCauseToCartDialog.AddToCart {
    private HomePresenter homePresenter;


    private View homeView;
    private LatestCausesAdapter latestCausesAdapter;
    private LatestProductsAdapter latestProductsAdapter;
    private UrgentCasesPagerAdapter urgentCasesPagerAdapter;
    private NewsAdapter homeNewsAdapter;
    private List<Products> latestCausesList;
    private List<Products> latestProductsList;
    private List<Products> urgentCaseList;
    // TODO: 1/29/18 A_M [new Task]
    private List<News> homeNewsList;
    private ProgressBar latestProductsProgress, latestCausesProgress, urgentCasesProgress, homeNewsProgress;
    private HomeActions homeActions;
    private CartAction cartAction;
    private ViewPager urgentCasesViewPager;
    private RelativeLayout checkAllCauses, checkAllProducts, checkAllHomeNews;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        overrideFonts(getContext(), homeView);
        return homeView;
    }

    @Override
    protected void initPresenter() {
        homePresenter = new HomePresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        boolean isReverse = homePresenter.isReverse(appLang);
        int defaultImageResId = ImageUtils.getDefaultImage(appLang);
        int arrowImage = ImageUtils.getArrow(appLang);

        urgentCasesProgress = homeView.findViewById(R.id.urgent_cases_progress_bar);
        latestProductsProgress = homeView.findViewById(R.id.latest_products_progress_bar);
        latestCausesProgress = homeView.findViewById(R.id.latest_causes_progress_bar);
        homeNewsProgress = homeView.findViewById(R.id.home_news_progress_bar);

        checkAllProducts = homeView.findViewById(R.id.check_products_layout);
        checkAllCauses = homeView.findViewById(R.id.check_causes_layout);
        checkAllHomeNews = homeView.findViewById(R.id.home_news_layout);


        checkAllProducts.setOnClickListener(v -> homeActions.onCheckAllProductsClicked());
        checkAllCauses.setOnClickListener(v -> homeActions.onCheckAllCausesClicked());
        checkAllHomeNews.setOnClickListener(v -> homeActions.onCheckAllHomeNewsClicked());

        urgentCasesViewPager = homeView.findViewById(R.id.urgent_cases_view_pager);
        RecyclerView latestProductsRecycler = homeView.findViewById(R.id.latest_products_recycler_view);
        RecyclerView latestCausesRecycler = homeView.findViewById(R.id.latest_causes_recycler_view);
        RecyclerView homeNewsRecycler = homeView.findViewById(R.id.home_news_recycler_view);
        ImageView productsImageView = homeView.findViewById(R.id.products_arrow);
        ImageView causesImageView = homeView.findViewById(R.id.causes_arrow);
        productsImageView.setImageResource(arrowImage);
        causesImageView.setImageResource(arrowImage);
        RecyclerView.LayoutManager latestProductsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager latestCausesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager latestNewsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        latestProductsRecycler.setLayoutManager(latestProductsLayoutManager);
        latestCausesRecycler.setLayoutManager(latestCausesLayoutManager);
        //todo A_M [New_task]
        homeNewsRecycler.setLayoutManager(latestNewsLayoutManager);

        latestCausesList = new ArrayList<>();
        latestCausesAdapter = new LatestCausesAdapter(getContext(), latestCausesList, defaultImageResId);
        latestCausesAdapter.setOnCauseClicked(this);
        latestCausesRecycler.setAdapter(latestCausesAdapter);

        latestProductsList = new ArrayList<>();
        latestProductsAdapter = new LatestProductsAdapter(getContext(), latestProductsList, defaultImageResId);
        latestProductsAdapter.setOnProductClicked(this);
        latestProductsRecycler.setAdapter(latestProductsAdapter);
        //todo A_M [New_task]
        homeNewsList = new ArrayList<>();
        homeNewsAdapter = new NewsAdapter(homeNewsList, getContext(), defaultImageResId, LinearLayout.VERTICAL);
        homeNewsAdapter.setOnNewsClicked(this);
        homeNewsRecycler.setAdapter(homeNewsAdapter);

        urgentCaseList = new ArrayList<>();
        urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(getContext(), urgentCaseList, defaultImageResId);
        urgentCasesPagerAdapter.setOnCaseClicked(this);
        urgentCasesViewPager.setAdapter(urgentCasesPagerAdapter);


        homePresenter.getLatestProducts(appLang);
        homePresenter.getUrgentCases(appLang);
        homePresenter.getLatestCauses(appLang);
        // TODO: 1/29/18 A_M [new Task]
        homePresenter.getHomeNews(appLang);
    }

    @Override
    public void onError(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLatestProducts(List<Products> latestProductsList) {
        this.latestProductsList.clear();
        this.latestProductsList.addAll(latestProductsList);
        latestProductsAdapter.notifyDataSetChanged();
        if (this.latestProductsList.isEmpty()) {
            hideLatestProducts();
        } else {
            showLatestProducts();
        }
    }

    @Override
    public void showLatestCauses(List<Products> latestCausesList) {

        this.latestCausesList.clear();
        this.latestCausesList.addAll(latestCausesList);
        latestCausesAdapter.notifyDataSetChanged();
        if (this.latestCausesList.isEmpty()) {
            hideLatestCauses();
        } else {
            showLatestCauses();
        }
    }

    @Override
    public void showUrgentCases(List<Products> urgentCaseList) {
        this.urgentCaseList.clear();
        this.urgentCaseList.addAll(urgentCaseList);
        urgentCasesPagerAdapter.notifyDataSetChanged();

        if (urgentCaseList.isEmpty()) {
            hideUrgentCases();
        } else {
            showUrgentCases();
        }

    }

    @Override
    public void showHomeNews(List<News> homeNewsList) {

        this.homeNewsList.clear();
        this.homeNewsList.addAll(homeNewsList);
        homeNewsAdapter.notifyDataSetChanged();

        if (homeNewsList.isEmpty()) {
            hideHomeNews();
        } else {
            showHomeNews();
        }

    }

    @Override
    public void showUrgentCasesLoading() {
        urgentCasesProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLatestProductsLoading() {
        latestProductsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLatestCausesLoading() {
        latestCausesProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHomeNewsLoading() {
        homeNewsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUrgentCasesLoading() {
        urgentCasesProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestProductsLoading() {
        latestProductsProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestCausesLoading() {
        latestCausesProgress.setVisibility(View.GONE);
    }

    public void hideHomeNewsLoading() {
        homeNewsProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestProducts() {
        checkAllProducts.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestCauses() {
        checkAllCauses.setVisibility(View.GONE);
    }

    // TODO: 1/29/18 A_M [new Task]
    @Override
    public void hideHomeNews() {
        checkAllHomeNews.setVisibility(View.GONE);
    }

    @Override
    public void hideUrgentCases() {
        urgentCasesViewPager.setVisibility(View.GONE);
    }


    @Override
    public void showLatestProducts() {
        checkAllProducts.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLatestCauses() {
        checkAllCauses.setVisibility(View.VISIBLE);
    }

    // TODO: 1/29/18 A_M [new Task]
    @Override
    public void showHomeNews() {
        checkAllHomeNews.setVisibility(View.VISIBLE);
    }


    @Override
    public void showUrgentCases() {
        urgentCasesViewPager.setVisibility(View.VISIBLE);
    }


    public void setHomeActions(HomeActions homeActions) {
        this.homeActions = homeActions;
    }

    @Override
    public void onProductClicked(int id) {
        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, id);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_products));
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
        startActivity(intent);
    }

    @Override
    public void onShareClicked(String url) {
        homePresenter.shareItem(url);
    }

    @Override
    public void onDonateLatestCauseClicked(Products latestCause) {
        showDialogFragment(latestCause);
        BaseFragment.sendTrackEvent(ConstUtil.CATEGORY_DONATION, ConstUtil.ACTION_ADD_TO_CART, PrefUtils.getUserId(getActivity()));
    }

    @Override
    public void onActionClicked(Products product) {
        showDialogFragment(product);
        //todo A_M [New_task]
        BaseFragment.sendTrackEvent(ConstUtil.CATEGORY_DONATION, ConstUtil.ACTION_ADD_TO_CART, PrefUtils.getUserId(getActivity()));
    }

    @Override
    public void onAddToCartClicked(Products product) {
        showDialogFragment(product);
        //todo A_M [New_task]
        BaseFragment.sendTrackEvent(ConstUtil.CATEGORY_DONATION, ConstUtil.ACTION_ADD_TO_CART, PrefUtils.getUserId(getActivity()));
    }

    private void showDialogFragment(Products item) {
        Bundle bundle = new Bundle();
//        bundle.putString(ProductDetailsFragment.ITEM_TITLE, item.getProductTitle());
        bundle.putParcelable(ProductDetailsFragment.EXTRA_ITEM, item);
        if (item.getProductType().equals(UrgentCasesPagerAdapter.PRODUCT_TYPE)) {
//            bundle.putDouble(ProductDetailsFragment.ITEM_PRICE, item.getProductPrice());
            AddProductToCartDialog addProductToCartDialog = new AddProductToCartDialog();
            addProductToCartDialog.setArguments(bundle);
            addProductToCartDialog.setAddToCart(this);
            addProductToCartDialog.show(getFragmentManager(), AddProductToCartDialog.class.getSimpleName());
        } else {
            AddCauseToCartDialog addCauseToCartDialog = new AddCauseToCartDialog();
            addCauseToCartDialog.setArguments(bundle);
            addCauseToCartDialog.setAddToCart(this);
            addCauseToCartDialog.show(getFragmentManager(), AddCauseToCartDialog.class.getSimpleName());
        }
    }

    @Override
    public void onCauseClicked(int causeId) {
        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, causeId);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_causes));
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
        startActivity(intent);
    }

    @Override
    public void onCaseClicked(int id, String productType) {
        String title;
        if (productType.equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            title = getString(R.string.title_causes);
        } else {
            title = getString(R.string.title_products);
        }

        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, id);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, title);
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
        startActivity(intent);
    }

    // TODO: 1/29/18 A_M [new Task]
    @Override
    public void onNewsClicked(int newsId) {
        Intent intent = NewsDetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, newsId);
        startActivity(intent);
    }

    public void setCartAction(CartAction cartAction) {
        this.cartAction = cartAction;
    }

    //    @Override
//    public void onAddToCartClicked(int quantity) {
//
//    }

//    @Override
//    public void onAddToCartClicked(double quantity) {
//
//    }

    @Override
    public void onItemInserted() {
        cartAction.onItemInserted();
    }

    public interface CartAction {
        void onItemInserted();
    }

    public interface HomeActions {
        void onCheckAllProductsClicked();

        void onCheckAllCausesClicked();

        void onCheckAllHomeNewsClicked();

    }
}
