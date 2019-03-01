package com.spade.mek.ui.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseSearchFragment;
import com.spade.mek.ui.cart.view.AddCauseToCartDialog;
import com.spade.mek.ui.cart.view.AddProductToCartDialog;
import com.spade.mek.ui.home.DetailsActivity;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.search.model.SearchResponse;
import com.spade.mek.ui.products.model.ProductsData;
import com.spade.mek.ui.products.presenter.ProductsPresenter;
import com.spade.mek.ui.products.presenter.ProductsPresenterImpl;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.ui.products.view.ProductsAdapter;
import com.spade.mek.ui.products.view.ProductsView;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsSearchFragment extends BaseSearchFragment implements ProductsView,
        ProductsAdapter.ProductActions, AddCauseToCartDialog.AddToCart, AddProductToCartDialog.AddToCart {

    private static ProductsPresenter productsPresenter;
    private ProductsAdapter productsAdapter;
    private List<Products> productsList;
    private View mProductsView;
    private ProgressBar productsProgressBar;
    private CartAction cartAction;
    private LinearLayout noSearchResult, lookingForLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductsView = inflater.inflate(R.layout.fragment_products, container, false);
        initViews();
        overrideFonts(getContext(), mProductsView);
        Tracker productsTracker = MekApplication.getDefaultTracker();
        productsTracker.setScreenName( getString(R.string.products_search));
        productsTracker.send(new HitBuilders.ScreenViewBuilder().build());
        return mProductsView;
    }

    @Override
    protected void initPresenter() {
        productsPresenter = new ProductsPresenterImpl(getContext());
        productsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        recyclerView = (RecyclerView) mProductsView.findViewById(R.id.products_recycler_view);
        lookingForLayout = (LinearLayout) mProductsView.findViewById(R.id.looking_for_layout);
        noSearchResult = (LinearLayout) mProductsView.findViewById(R.id.nothing_matches_layout);
        productsProgressBar = (ProgressBar) mProductsView.findViewById(R.id.products_progress_bar);
        recyclerView.setVisibility(View.GONE);
        lookingForLayout.setVisibility(View.VISIBLE);

        productsList = new ArrayList<>();
        List<Products> urgentCaseList = new ArrayList<>();
        String appLang = PrefUtils.getAppLang(getContext());

        productsAdapter = new ProductsAdapter(productsList, urgentCaseList, ImageUtils.getDefaultImage(appLang), getString(R.string.all_products), SearchActivity.SEARCH_VIEW, getContext());
        productsAdapter.setProductActions(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.HEADER_TYPE:
                        return 2;
                    case ProductsAdapter.ITEM_TYPE:
                        return 1;
                }
                return 1;
            }
        });
        recyclerView.setAdapter(productsAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
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
    public void showUrgentCases(List<Products> urgentCaseList) {
    }

    @Override
    public void showAllProducts(ProductsData productsData) {
    }

    @Override
    public void showFilteredProducts(ProductsData productsData) {
    }

    @Override
    public void showUrgentCasesLoading() {

    }

    @Override
    public void hideUrgentCasesLoading() {

    }

    @Override
    public void showProductsLoading() {
        productsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProductsLoading() {
        productsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResults(SearchResponse searchResponse) {
        this.productsList.clear();

        if (searchResponse.getItemsList() != null) {
            this.productsList.addAll(searchResponse.getItemsList());
        }
        productsAdapter.notifyDataSetChanged();
        lookingForLayout.setVisibility(View.GONE);
        if (this.productsList.isEmpty()) {
            noSearchResult.setVisibility(View.VISIBLE);
        } else {
            noSearchResult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProductClicked(int productId) {
        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, productId);
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_products));
        startActivity(intent);
    }

    @Override
    public void onShareClicked(String url) {
        productsPresenter.shareItem(url);
    }

    @Override
    public void onAddToCartClicked(Products product) {
        showDialogFragment(product);
    }

    private void showDialogFragment(Products item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductDetailsFragment.EXTRA_ITEM, item);
        if (item.getProductType().equals(UrgentCasesPagerAdapter.PRODUCT_TYPE)) {
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

    public void setCartAction(CartAction cartAction) {
        this.cartAction = cartAction;
    }

    @Override
    public void onItemInserted() {
        cartAction.onItemInserted();
    }

    @Override
    public void search(String searchKeyword) {
        productsPresenter.search(searchKeyword);
    }

    public interface CartAction {
        void onItemInserted();
    }

}
