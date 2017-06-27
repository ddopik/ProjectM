package com.spade.mek.ui.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.presenter.CartPresenter;
import com.spade.mek.ui.cart.presenter.CartPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import io.realm.Realm;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class CartFragment extends BaseFragment implements CartView, CartRealmAdapter.CartActions {

    private View cartView;
    private LinearLayout receiptLayout;
    private RecyclerView cartRecyclerView;
    private TextView totalItems, totalCost;
    private Button checkOutButton;
    private CartPresenter cartPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews();
        return cartView;
    }

    @Override
    protected void initPresenter() {
        cartPresenter = new CartPresenterImpl(getContext());
        cartPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        Realm realm = Realm.getDefaultInstance();
        String appLang = PrefUtils.getAppLang(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        CartRealmAdapter cartAdapter = new CartRealmAdapter(realm.where(CartItem.class).equalTo("userId", PrefUtils.getUserId(getContext())).findAll(), true);

        cartRecyclerView = (RecyclerView) cartView.findViewById(R.id.cart_recycler_view);
        checkOutButton = (Button) cartView.findViewById(R.id.check_out_btn);
        receiptLayout = (LinearLayout) cartView.findViewById(R.id.receipt_layout);
        totalCost = (TextView) cartView.findViewById(R.id.total_cost);
        totalItems = (TextView) cartView.findViewById(R.id.total_items);

        cartAdapter.setUpAdapter(getContext(), ImageUtils.getDefaultImage(appLang));
        cartAdapter.setCartActions(this);

        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(cartAdapter);
        checkOutButton.setOnClickListener(v -> {
            startActivity(UserDataActivity.getLaunchIntent(getContext()));
            finish();
        });
        hideEmptyScreen();
        updateUI();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void updateUI() {
        long itemsCount = cartPresenter.getItemsCount();
        if (itemsCount > 0) {
            hideEmptyScreen();
            totalCost.setText(String.format(getString(R.string.egp),
                    String.valueOf(cartPresenter.getTotalCost())));
            totalItems.setText(String.format(getString(R.string.total_items), String.valueOf(cartPresenter.getItemsCount())));
        } else showEmptyScreen();
    }

    @Override
    public void showEmptyScreen() {
        cartRecyclerView.setVisibility(View.GONE);
        receiptLayout.setVisibility(View.GONE);
        checkOutButton.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyScreen() {
        cartRecyclerView.setVisibility(View.VISIBLE);
        receiptLayout.setVisibility(View.VISIBLE);
        checkOutButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onIncreaseClicked(CartItem cartItem, int position) {
        cartPresenter.increaseItemQuantity(cartItem, position);
        updateUI();
    }

    @Override
    public void onDecreaseClicked(CartItem cartItem, int position) {
        cartPresenter.decreaseItemQuantity(cartItem, position);
        updateUI();
    }

    @Override
    public void onClearClicked(CartItem cartItem, int position) {
        cartPresenter.deleteItem(cartItem, position);
        updateUI();
    }
}
