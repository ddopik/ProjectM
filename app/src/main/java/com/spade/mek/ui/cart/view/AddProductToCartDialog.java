package com.spade.mek.ui.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.presenter.AddToCartPresenter;
import com.spade.mek.ui.cart.presenter.AddToCartPresenterImpl;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class AddProductToCartDialog extends DialogFragment {
    public AddToCart addToCart;
    private String title;
    private double price;
    private int quantityAmount = 1;
    private EditText quantityEditText;
    private TextView totalCost;
    private AddToCartPresenter addToCartPresenter;
    private Products product;
    private View dialogView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable(ProductDetailsFragment.EXTRA_ITEM);
        if (product != null) {
            title = product.getProductTitle();
            price = product.getProductPrice();
        }
        initPresenter();
    }

    private void initPresenter() {
        addToCartPresenter = new AddToCartPresenterImpl(getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.dialog_product, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        FontUtils.overrideFonts(getContext(), dialogView);
        sendDonateEvent();
        return dialogView;
    }
    private void init(View view) {
        quantityEditText = view.findViewById(R.id.quantityEditText);
        totalCost = view.findViewById(R.id.total_price);
        totalCost.setVisibility(View.VISIBLE);
        quantityEditText.setEnabled(false);
        quantityEditText.setHint("");
        ImageView increaseImage = view.findViewById(R.id.arrow_up);
        ImageView decreaseImage = view.findViewById(R.id.arrow_down);

        TextView itemTitle = view.findViewById(R.id.item_title);
        TextView itemPrice = view.findViewById(R.id.item_price);

        Button addToCartButton = view.findViewById(R.id.add_to_cart_btn);
        itemTitle.setText(title);
        itemPrice.setVisibility(View.VISIBLE);
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

        addToCartButton.setOnClickListener(v -> {
//                addToCart.onAddToCartClicked(quantityAmount))
            addToCartPresenter.addItemToCart(product, quantityAmount);
            addToCart.onItemInserted();
            dismiss();
        });
        setCostTest();
    }

    private void setCostTest() {
        quantityEditText.setText(String.valueOf(quantityAmount));
        totalCost.setText(String.format(getString(R.string.egp), String.valueOf(price * quantityAmount)));
    }

    public void setAddToCart(AddToCart addToCart) {
        this.addToCart = addToCart;
    }

    //todo A_M [New_task]
    public void sendDonateEvent() {
        Bundle bundle = getArguments();
        if (!bundle.getString("category_event", "").equals("") && !bundle.getString("category_action", "").equals("")) {
            BaseFragment.sendTrackEvent(bundle.getString("category_event"), bundle.getString("category_action"), PrefUtils.getUserId(getActivity()));
        }

    }

    public interface AddToCart {
        void onItemInserted();
    }


}
