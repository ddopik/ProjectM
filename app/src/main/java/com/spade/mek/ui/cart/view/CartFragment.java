package com.spade.mek.ui.cart.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.presenter.CartPresenter;
import com.spade.mek.ui.cart.presenter.CartPresenterImpl;
import com.spade.mek.ui.login.view.LoginDialogFragment;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.ConstUtil;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

import io.realm.Realm;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class CartFragment extends BaseFragment implements CartView, CartRealmAdapter.CartActions, LoginDialogFragment.LoginDialogActions {

    private static final String NO_MONEY = "00";
    private View cartView;
    private TextView totalItems, totalCost;
    private TextView totalEmptyItems, totalEmptyCost;
    private CartPresenter cartPresenter;
    private RelativeLayout cartLayout, cartEmptyLayout;
    private ProgressBar progressBar;
    private CartRealmAdapter cartAdapter;
    private RecyclerView cartRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews();
        overrideFonts(getContext(), cartView);
        return cartView;
    }

    @Override
    protected void initPresenter() {
        cartPresenter = new CartPresenterImpl(getContext());
        cartPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        cartRecyclerView = cartView.findViewById(R.id.cart_recycler_view);
        Button checkOutButton = cartView.findViewById(R.id.check_out_btn);
        cartLayout = cartView.findViewById(R.id.cart_layout);
        cartEmptyLayout = cartView.findViewById(R.id.empty_cart_layout);
        totalItems = cartView.findViewById(R.id.total_items);
        totalCost = cartView.findViewById(R.id.total_cost);
        totalEmptyItems = cartView.findViewById(R.id.total_empty_items);
        totalEmptyCost = cartView.findViewById(R.id.total_empty_cost);
        progressBar = cartView.findViewById(R.id.progress_bar);

        cartRecyclerView.setLayoutManager(layoutManager);
        checkOutButton.setOnClickListener(v -> checkIfLoggedIn());
        hideEmptyScreen();
//        updateUI();
        getUpdatedData();
    }

    private void checkIfLoggedIn() {

        int loginProvider = PrefUtils.getLoginProvider(getContext());
        if (loginProvider == LoginProviders.NONE.getLoginProviderCode()) {
            showLoginDialog();
        } else {
            BaseFragment.sendTrackEvent(ConstUtil.CATEGORY_DONATION, ConstUtil.ACTION_CHECK_OUT, PrefUtils.getUserId(getActivity()));
            navigateToUserDataActivity();
        }
    }

    private void showLoginDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(RegisterActivity.EXTRA_TYPE, RegisterActivity.CHECKOUT_TYPE);
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setLoginDialogActions(this);
        loginDialogFragment.setArguments(bundle);
        loginDialogFragment.show(getChildFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    @Override
    public void navigateToUserDataActivity() {
        Intent intent = UserDataActivity.getLaunchIntent(getContext());
        intent.putExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_PAY_FOR_PRODUCTS);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void updateUI() {
//        this.cartItems.clear();
//        this.cartItems.addAll(cartPresenter.getCartItems(PrefUtils.getUserId(getContext())));
//        cartAdapter.notifyDataSetChanged();
        Realm realm = Realm.getDefaultInstance();
        String appLang = PrefUtils.getAppLang(getContext());
        cartAdapter = new CartRealmAdapter(realm.where(CartItem.class).equalTo("userId", PrefUtils.getUserId(getContext())).findAll(), true);
        cartAdapter.setUpAdapter(getContext(), ImageUtils.getDefaultImage(appLang));
        cartAdapter.setCartActions(this);
        cartRecyclerView.setAdapter(cartAdapter);

        totalCost.setText(String.format(getString(R.string.egp),
                String.valueOf(cartPresenter.getTotalCost())));
        totalItems.setText(getResources().getQuantityString(R.plurals.total_items_plural, (int) cartPresenter.getItemsCount(), (int) cartPresenter.getItemsCount()));
    }

    private void getUpdatedData() {
        long itemsCount = cartPresenter.getItemsCount();
        if (itemsCount > 0) {
            hideEmptyScreen();
            cartPresenter.updateCartItemsData();
        } else showEmptyScreen();
    }


    @Override
    public void showEmptyScreen() {
        cartEmptyLayout.setVisibility(View.VISIBLE);
        cartLayout.setVisibility(View.GONE);
        totalEmptyCost.setText(String.format(getString(R.string.egp),
                NO_MONEY));
        totalEmptyItems.setText(getResources().getQuantityString(R.plurals.total_items_plural, 0, 0));
    }

    @Override
    public void hideEmptyScreen() {
        cartEmptyLayout.setVisibility(View.GONE);
        cartLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void loginSuccess() {

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

    @Override
    public void loginAsGuest() {
        navigateToUserDataActivity();
    }

    @Override
    public void onLoginSuccess() {
        cartPresenter.updateUserCartItems(PrefUtils.getUserId(getContext()));
    }

}
