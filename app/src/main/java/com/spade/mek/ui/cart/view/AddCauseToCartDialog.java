package com.spade.mek.ui.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.cart.presenter.AddToCartPresenter;
import com.spade.mek.ui.cart.presenter.AddToCartPresenterImpl;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.view.ProductDetailsFragment;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class AddCauseToCartDialog extends DialogFragment {
    private String title;
    private double quantityAmount = 0;
    private EditText quantityEditText;
    //    private TextView totalCost;
    public AddToCart addToCart;
    private AddToCartPresenter addToCartPresenter;
    private Products product;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable(ProductDetailsFragment.EXTRA_ITEM);
        if (product != null) {
            title = product.getProductTitle();
        }
        initPresenter();
    }

    private void initPresenter() {
        addToCartPresenter = new AddToCartPresenterImpl(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_product, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        return dialogView;
    }

    private void init(View view) {
        quantityEditText = (EditText) view.findViewById(R.id.quantityEditText);
//        totalCost = (TextView) view.findViewById(R.id.total_price);

        ImageView increaseImage = (ImageView) view.findViewById(R.id.arrow_up);
        ImageView decreaseImage = (ImageView) view.findViewById(R.id.arrow_down);

        TextView itemTitle = (TextView) view.findViewById(R.id.item_title);
        TextView currencyTitle = (TextView) view.findViewById(R.id.currency_title);
        currencyTitle.setVisibility(View.VISIBLE);

        Button addToCartButton = (Button) view.findViewById(R.id.add_to_cart_btn);
        itemTitle.setText(title);

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        quantityAmount = Double.parseDouble(s.toString());
                    } catch (NumberFormatException e) {
                        quantityAmount = 1;
                        setMoneyAmount();
                    }
                } else {
                    quantityAmount = 0;
//                    setMoneyAmount();
                }
            }
        });

        increaseImage.setOnClickListener(v -> {
            quantityAmount += 1;
            setMoneyAmount();
        });

        decreaseImage.setOnClickListener(v -> {
            if (quantityAmount > 1) {
                quantityAmount -= 1;
                setMoneyAmount();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            if (quantityAmount < 1) {
                quantityEditText.setError(getString(R.string.add_money_description));
            } else {
                addToCartPresenter.addItemToCart(product, quantityAmount);
                addToCart.onItemInserted();
                dismiss();
            }
        });
//            addToCart.onAddToCartClicked(quantityAmount)});
//        setMoneyAmount();
    }

    private void setMoneyAmount() {
        quantityEditText.setText(String.format("%.2f", quantityAmount));
    }

    public void setAddToCart(AddToCart addToCart) {
        this.addToCart = addToCart;
    }

    public interface AddToCart {
        void onItemInserted();
    }

}
