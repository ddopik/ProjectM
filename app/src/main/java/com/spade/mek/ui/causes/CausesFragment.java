package com.spade.mek.ui.causes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.NavigationManager;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class CausesFragment extends BaseFragment implements CausesView, CausesAdapter.CausesAction {

    private CausesPresenter causesPresenter;
    private CausesAdapter causesAdapter;
    private List<Products> urgentCaseList;
    private List<Products> productsList;
    private View mProductsView;
    private ProgressBar productsProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductsView = inflater.inflate(R.layout.fragment_products, container, false);
        initViews();
        return mProductsView;
    }

    @Override
    protected void initPresenter() {
        causesPresenter = new CausesPresenterImpl(getContext());
        causesPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        RecyclerView recyclerView = (RecyclerView) mProductsView.findViewById(R.id.products_recycler_view);
        productsProgressBar = (ProgressBar) mProductsView.findViewById(R.id.products_progress_bar);
        productsList = new ArrayList<>();
        urgentCaseList = new ArrayList<>();
        String appLang = PrefUtils.getAppLang(getContext());

        causesAdapter = new CausesAdapter(getContext(), productsList, urgentCaseList, ImageUtils.getDefaultImage(appLang));
        causesAdapter.setProductActions(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch (productsAdapter.getItemViewType(position)) {
//                    case ProductsAdapter.HEADER_TYPE:
//                        return 2;
//                    case ProductsAdapter.ITEM_TYPE:
//                        return 1;
//                }
//                return 1;
//            }
//        });
        recyclerView.setAdapter(causesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        causesPresenter.getAllCauses(appLang, 1);
        causesPresenter.getUrgentCases(appLang);
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
        causesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCauses(AllCausesResponse allCausesResponse) {
        this.productsList.clear();
        if (allCausesResponse.getProductsData() != null && allCausesResponse.getProductsData().getProductsList() != null) {
            this.productsList.addAll(allCausesResponse.getProductsData().getProductsList());
            causesAdapter.notifyDataSetChanged();
        }
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
    public void onCauseClicked(int productId) {
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProductDetailsFragment.ITEM_ID, productId);
        productDetailsFragment.setArguments(bundle);
        NavigationManager.openFragment(R.id.fragment_container, productDetailsFragment, (AppCompatActivity) getActivity(), ProductDetailsFragment.class.getSimpleName());
    }
}
