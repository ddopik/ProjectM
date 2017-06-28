package com.spade.mek.ui.products.view;

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
import com.spade.mek.ui.cart.view.AddCauseToCartDialog;
import com.spade.mek.ui.cart.view.AddProductToCartDialog;
import com.spade.mek.ui.home.DetailsActivity;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.model.ProductsData;
import com.spade.mek.ui.products.presenter.ProductsPresenter;
import com.spade.mek.ui.products.presenter.ProductsPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsFragment extends BaseFragment implements ProductsView,
        ProductsAdapter.ProductActions, AddCauseToCartDialog.AddToCart, AddProductToCartDialog.AddToCart {

    private ProductsPresenter productsPresenter;
    private ProductsAdapter productsAdapter;
    private List<Products> urgentCaseList;
    private List<Products> productsList;
    private View mProductsView;
    private ProgressBar productsProgressBar;
    private boolean isLoading = false;
    private int currentPage = 0;
    private int lastPage;
    private String appLang;
    private CartAction cartAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductsView = inflater.inflate(R.layout.fragment_products, container, false);
        initViews();
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
        productsProgressBar = (ProgressBar) mProductsView.findViewById(R.id.products_progress_bar);
        productsList = new ArrayList<>();
        urgentCaseList = new ArrayList<>();
        appLang = PrefUtils.getAppLang(getContext());

        productsAdapter = new ProductsAdapter(productsList, urgentCaseList, ImageUtils.getDefaultImage(appLang), getString(R.string.all_products), getContext());
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

        getProducts();
        productsPresenter.getUrgentCases(appLang);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (currentPage < lastPage)) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        getProducts();
                    }
                }
            }
        });
    }

    private void getProducts() {
        int pageNumber = currentPage + 1;
        isLoading = true;
        productsPresenter.getAllProducts(appLang, pageNumber);

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
    public void showUrgentCases(List<Products> urgentCaseList) {
        this.urgentCaseList.clear();
        this.urgentCaseList.addAll(urgentCaseList);
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllProducts(ProductsData productsData) {
        currentPage = productsData.getCurrentPage();
        lastPage = productsData.getLastPage();
        isLoading = false;
        if (productsData.getProductsList() != null) {
            this.productsList.addAll(productsData.getProductsList());
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

    public interface CartAction {
        void onItemInserted();
    }

}
