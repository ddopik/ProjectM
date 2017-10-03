package com.spade.mek.ui.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseSearchFragment;
import com.spade.mek.ui.cart.view.AddCauseToCartDialog;
import com.spade.mek.ui.cart.view.AddProductToCartDialog;
import com.spade.mek.ui.causes.AllCausesResponse;
import com.spade.mek.ui.causes.CausesAdapter;
import com.spade.mek.ui.causes.CausesPresenter;
import com.spade.mek.ui.causes.CausesPresenterImpl;
import com.spade.mek.ui.causes.CausesView;
import com.spade.mek.ui.home.DetailsActivity;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.search.model.SearchResponse;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class CausesSearchFragment extends BaseSearchFragment implements CausesView,
        CausesAdapter.CausesAction, AddProductToCartDialog.AddToCart, AddCauseToCartDialog.AddToCart {

    private static CausesPresenter causesPresenter;
    private CausesAdapter causesAdapter;
    private List<Products> urgentCaseList;
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
        return mProductsView;
    }

    @Override
    protected void initPresenter() {
        causesPresenter = new CausesPresenterImpl(getContext());
        causesPresenter.setView(this);
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
        urgentCaseList = new ArrayList<>();
        String appLang = PrefUtils.getAppLang(getContext());

        causesAdapter = new CausesAdapter(getContext(), productsList, urgentCaseList, getString(R.string.all_causes), SearchActivity.SEARCH_VIEW, ImageUtils.getDefaultImage(appLang));
        causesAdapter.setProductActions(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(causesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
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
        this.urgentCaseList.clear();
        this.urgentCaseList.addAll(urgentCaseList);
        causesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCauses(AllCausesResponse allCausesResponse) {
        if (allCausesResponse.getProductsData() != null && allCausesResponse.getProductsData().getProductsList() != null) {
            this.productsList.addAll(allCausesResponse.getProductsData().getProductsList());
            causesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showAFilteredCauses(AllCausesResponse allCausesResponse) {
    }

    @Override
    public void showUrgentCasesLoading() {

    }

    @Override
    public void hideUrgentCasesLoading() {

    }

    @Override
    public void showCausesLoading() {
        productsProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideCausesLoading() {
        productsProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showSearchResults(SearchResponse searchResponse) {
        this.productsList.clear();
        if (searchResponse != null && searchResponse.getItemsList() != null) {
            this.productsList.addAll(searchResponse.getItemsList());
            causesAdapter.notifyDataSetChanged();
        }

        lookingForLayout.setVisibility(View.GONE);
        if (this.productsList.isEmpty()) {
            noSearchResult.setVisibility(View.VISIBLE);
        } else {
            noSearchResult.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCauseClicked(int productId) {
        Intent intent = DetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, productId);
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, ProductDetailsFragment.EXTRA_NORMAL_PRODUCT);
        intent.putExtra(DetailsActivity.SCREEN_TITLE, getString(R.string.title_causes));
        startActivity(intent);
    }

    @Override
    public void onShareClicked(String url) {
        causesPresenter.shareItem(url);
    }

    @Override
    public void onDonateCauseClicked(Products cause) {
        showDialogFragment(cause);
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
        causesPresenter.search(searchKeyword);
    }

    public interface CartAction {
        void onItemInserted();
    }
}
