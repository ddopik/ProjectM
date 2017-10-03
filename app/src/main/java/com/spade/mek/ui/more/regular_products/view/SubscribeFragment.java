package com.spade.mek.ui.more.regular_products.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.regular_products.presenter.SubscribePresenter;
import com.spade.mek.ui.more.regular_products.presenter.SubscribePresenterImpl;
import com.spade.mek.ui.products.view.ProductDetailsFragment;

/**
 * Created by Ayman Abouzeid on 8/22/17.
 */

public class SubscribeFragment extends BaseFragment implements SubscribeView, ConfirmSubscriptionDialog.ConfirmActions {
    public static final String EXTRA_DURATION = "EXTRA_DURATION";
    private View mSubscribeView;
    private Products product;
    private int quantity = 1, duration = 1;
    private TextView quantityText, totalAmount;
    private ProgressDialog progressDialog;
    private SubscribePresenter subscribePresenter;
    private double totalCost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable(ProductDetailsFragment.EXTRA_PRODUCT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubscribeView = inflater.inflate(R.layout.fragment_subscribe, container, false);
        initViews();
        return mSubscribeView;
    }

    @Override
    protected void initPresenter() {
        subscribePresenter = new SubscribePresenterImpl(getContext());
        subscribePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        RadioGroup monthsRadioGroup = mSubscribeView.findViewById(R.id.months_radio_group);
        TextView productTitle = mSubscribeView.findViewById(R.id.product_title);
        quantityText = mSubscribeView.findViewById(R.id.quantity_text_view);
        totalAmount = mSubscribeView.findViewById(R.id.total_item_price);
        ImageView increaseImage = mSubscribeView.findViewById(R.id.arrow_up);
        ImageView decreaseImage = mSubscribeView.findViewById(R.id.arrow_down);
        Button submitButton = mSubscribeView.findViewById(R.id.submit_btn);
        productTitle.setText(product.getProductTitle());

        monthsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.one_month_radio_btn) {
                Log.d("radio", "1");
                duration = 1;
            } else if (checkedId == R.id.three_month_radio_btn) {
                Log.d("radio", "3");
                duration = 3;
            } else if (checkedId == R.id.six_month_radio_btn) {
                Log.d("radio", "6");
                duration = 6;
            }
        });

        increaseImage.setOnClickListener(v -> {
            quantity += 1;
            setQuantityAndCost();
        });

        decreaseImage.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity -= 1;
                setQuantityAndCost();
            }
        });

        submitButton.setOnClickListener(v -> subscribePresenter.subscribeToProduct(product, totalCost, quantity, duration));
        setQuantityAndCost();
    }

    private void setQuantityAndCost() {
        totalCost = product.getProductPrice() * quantity;
        totalAmount.setText(String.format(getString(R.string.egp), String.valueOf(totalCost)));
        quantityText.setText(String.valueOf(quantity));
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.subscribing));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void showConfirmationDialog() {
        Bundle bundle = new Bundle();
        bundle.putString(ProductDetailsFragment.EXTRA_PRODUCT_TITLE, product.getProductTitle());
        bundle.putString(SubscribeFragment.EXTRA_DURATION, String.valueOf(duration));
        ConfirmSubscriptionDialog confirmSubscriptionDialog = new ConfirmSubscriptionDialog();
        confirmSubscriptionDialog.setArguments(bundle);
        confirmSubscriptionDialog.setConfirmActions(this);
        confirmSubscriptionDialog.show(getChildFragmentManager(), ConfirmSubscriptionDialog.class.getSimpleName());
    }

    @Override
    public void onDismiss() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
