package com.spade.mek.ui.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.urgent_cases.UrgentCase;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsFragment extends BaseFragment implements ProductsView {

    private ProductsPresenter productsPresenter;
    private ProductsAdapter productsAdapter;
    private List<UrgentCase> urgentCaseList;
    private List<Products> productsList;
    private View mProductsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductsView = inflater.inflate(R.layout.fragment_products, container, false);
        return mProductsView;
    }

    @Override
    protected void initPresenter() {
        productsPresenter = new ProductsPresenterImpl(getContext());
        productsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        RecyclerView recyclerView = (RecyclerView) mProductsView.findViewById(R.id.products_recycler_view);

        productsList = new ArrayList<>();
        urgentCaseList = new ArrayList<>();
        String appLang = PrefUtils.getAppLang(getContext());

        productsAdapter = new ProductsAdapter(productsList, urgentCaseList, ImageUtils.getDefaultImage(appLang), getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.HEADER_TYPE:
                        return 1;
                    case ProductsAdapter.ITEM_TYPE:
                        return 2;
                }
                return 1;
            }
        });
        recyclerView.setAdapter(productsAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        productsPresenter.getAllProducts(appLang, 1);
        productsPresenter.getUrgentCases(appLang);
    }


    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();

    }

    @Override
    public void showUrgentCases(List<UrgentCase> urgentCaseList) {
        this.urgentCaseList.clear();
        this.urgentCaseList.addAll(urgentCaseList);
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllProducts(AllProductsResponse allProductsResponse) {
        this.productsList.clear();
        if (allProductsResponse.getProductsList() != null) {
            this.productsList.addAll(allProductsResponse.getProductsList());
            productsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void showUrgentCasesLoading() {

    }

    @Override
    public void hideUrgentCasesLoading() {

    }

    @Override
    public void showProductsLoading() {

    }

    @Override
    public void hideProductsLoading() {

    }
}
