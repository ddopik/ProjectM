package com.spade.mek.ui.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.products.view.ProductDetailsFragment;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class AddProductToCartDialog extends DialogFragment {
    private String title;
    private double price;
    private int quantityAmount = 1;
    private EditText quantityEditText;
    private TextView totalCost;
    public AddToCart addToCart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ProductDetailsFragment.ITEM_TITLE);
        price = getArguments().getDouble(ProductDetailsFragment.ITEM_PRICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_product, container, false);
        init(dialogView);
        return dialogView;
    }

    private void init(View view) {
        quantityEditText = (EditText) view.findViewById(R.id.quantityEditText);
        totalCost = (TextView) view.findViewById(R.id.total_price);
        quantityEditText.setEnabled(false);

        ImageView increaseImage = (ImageView) view.findViewById(R.id.arrow_up);
        ImageView decreaseImage = (ImageView) view.findViewById(R.id.arrow_down);

        TextView itemTitle = (TextView) view.findViewById(R.id.item_title);
        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);

        Button addToCartButton = (Button) view.findViewById(R.id.add_to_cart_btn);
        itemTitle.setText(title);
        itemPrice.setText(String.format(getString(R.string.egp), String.valueOf(price)));

        increaseImage.setOnClickListener(v -> {
            quantityAmount += 1;
            setCostTest();
        });

        decreaseImage.setOnClickListener(v -> {
            if (quantityAmount > 1) {
                quantityAmount -= 1;
                setCostTest();
            }
        });

        addToCartButton.setOnClickListener(v -> addToCart.onAddToCartClicked(quantityAmount));
        setCostTest();
    }

    private void setCostTest() {
        quantityEditText.setText(String.valueOf(quantityAmount));
        totalCost.setText(String.format(getString(R.string.egp), String.valueOf(price * quantityAmount)));
    }

    public void setAddToCart(AddToCart addToCart) {
        this.addToCart = addToCart;
    }

    public interface AddToCart {
        void onAddToCartClicked(int quantity);
    }

}
