package com.spade.mek.ui.more.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.DetailsActivity;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.regular_products.presenter.RegularProductsPresenter;
import com.spade.mek.ui.more.regular_products.presenter.RegularProductsPresenterImpl;
import com.spade.mek.ui.more.regular_products.view.RegularProductsView;
import com.spade.mek.ui.products.model.ProductsData;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProfileRegularProductsFragment extends BaseFragment implements RegularProductsView, ProfileRegularProductsAdapter.ProductActions {

    private static RegularProductsPresenter regularProductsPresenter;
    private ProfileRegularProductsAdapter regularProductsAdapter;
    private List<Products> productsList;
    private View mRegularProductsView;
    private ProgressBar productsProgressBar;
    private String appLang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRegularProductsView = inflater.inflate(R.layout.fragment_products, container, false);
        initViews();
        overrideFonts(getContext(), mRegularProductsView);
        return mRegularProductsView;
    }

    @Override
    protected void initPresenter() {
        regularProductsPresenter = new RegularProductsPresenterImpl(getContext());
        regularProductsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        RecyclerView recyclerView = (RecyclerView) mRegularProductsView.findViewById(R.id.products_recycler_view);
        productsProgressBar = (ProgressBar) mRegularProductsView.findViewById(R.id.products_progress_bar);
        productsList = new ArrayList<>();
        appLang = PrefUtils.getAppLang(getContext());

        regularProductsAdapter = new ProfileRegularProductsAdapter(productsList, ImageUtils.getDefaultImage(appLang), getContext());
        regularProductsAdapter.setProductActions(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(regularProductsAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        getProducts();
    }

    private void getProducts() {
        regularProductsPresenter.getProfileRegularProducts(appLang, PrefUtils.getUserToken(getContext()));
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
    public void showRegularProducts(ProductsData productsData) {
    }

    @Override
    public void showRegularProducts(List<Products> productsList) {
        if (productsList != null) {
            this.productsList.addAll(productsList);
            regularProductsAdapter.notifyDataSetChanged();
        }
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
    public void onProductClicked(int productId) {
        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, productId);
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_REGULAR_PRODUCT);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.regular_donations));
        startActivity(intent);
    }


}
