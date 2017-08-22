package com.spade.mek.ui.products.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.view.AddCauseToCartDialog;
import com.spade.mek.ui.cart.view.AddProductToCartDialog;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.ProductCategory;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.regular_products.view.SubscribeActivity;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenter;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsFragment extends BaseFragment implements ProductDetailsView,
        AddProductToCartDialog.AddToCart,
        AddCauseToCartDialog.AddToCart {
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_TITLE = "ITEM_TITLE";
    public static final String ITEM_PRICE = "ITEM_PRICE";
    public static final String EXTRA_ITEM = "EXTRA_ITEM";


    private View productDetailsView;
    private TextView productTitle, productCategory, productDetails,
            productCreatedAt, productPrice, productHashTag, remainingAmount,
            causeTargetTextView, causeCurrentAmount, subscribeTextView;
    private FrameLayout urgentLabel;
    private RelativeLayout causeProgressLayout;
    private ImageView shareImage;
    private SeekBar causeSeekBar;
    private ProgressBar progressBar;

    private ProductDetailsPresenter productDetailsPresenter;
    private ImagesPagerAdapter imagesPagerAdapter;

    private List<String> imagesList;
    private Products item;

    private int itemId;
    private String itemUrl = "";
    private CartAction cartAction;
    private Button donateNowBtn;
    private boolean isRegularProduct = false, isSubscribed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getInt(ITEM_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        productDetailsView = inflater.inflate(R.layout.fragment_product_details, container, false);
        initViews();
        overrideFonts(getContext(), productDetailsView);
        return productDetailsView;
    }

    @Override
    protected void initPresenter() {
        productDetailsPresenter = new ProductDetailsPresenterImpl(getContext());
        productDetailsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        imagesList = new ArrayList<>();
        ViewPager imagesViewPager = (ViewPager) productDetailsView.findViewById(R.id.product_images_view_pager);
        donateNowBtn = (Button) productDetailsView.findViewById(R.id.donate_now_btn);
        String appLang = PrefUtils.getAppLang(getContext());

        productTitle = (TextView) productDetailsView.findViewById(R.id.item_title);
        productCategory = (TextView) productDetailsView.findViewById(R.id.item_category);
        productDetails = (TextView) productDetailsView.findViewById(R.id.item_details);
        productPrice = (TextView) productDetailsView.findViewById(R.id.item_price);
        productCreatedAt = (TextView) productDetailsView.findViewById(R.id.item_publish_date);
        productHashTag = (TextView) productDetailsView.findViewById(R.id.item_hash_tag);
        causeTargetTextView = (TextView) productDetailsView.findViewById(R.id.cause_target);
        causeCurrentAmount = (TextView) productDetailsView.findViewById(R.id.cause_current_state);
        remainingAmount = (TextView) productDetailsView.findViewById(R.id.remaining_amount_text_view);
        subscribeTextView = (TextView) productDetailsView.findViewById(R.id.subscribe_text);
        shareImage = (ImageView) productDetailsView.findViewById(R.id.share_image_view);
        progressBar = (ProgressBar) productDetailsView.findViewById(R.id.progress_bar);
        causeSeekBar = (SeekBar) productDetailsView.findViewById(R.id.cause_target_progress_bar);
        urgentLabel = (FrameLayout) productDetailsView.findViewById(R.id.urgent_case_label);
        causeProgressLayout = (RelativeLayout) productDetailsView.findViewById(R.id.cause_progress_layout);

        imagesPagerAdapter = new ImagesPagerAdapter(getContext(), imagesList, ImageUtils.getDefaultImage(appLang));
        imagesViewPager.setAdapter(imagesPagerAdapter);

        shareImage.setOnClickListener(v -> productDetailsPresenter.shareItem(itemUrl));
        productDetailsPresenter.getProductDetails(appLang, itemId);

        donateNowBtn.setOnClickListener(v -> {
            showDialogFragment();
        });

        subscribeTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        subscribeTextView.setOnClickListener(v -> {
            if (isSubscribed) {

            } else {
                Intent intent = SubscribeActivity.getLaunchIntent(getContext());
                intent.putExtra(EXTRA_PRODUCT, item);
                startActivity(intent);
            }
        });
    }

    private void showDialogFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ITEM, item);

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

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private String getDate(long timeStamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        DateFormat dateFormatter = DateFormat.getDateInstance();
        dateFormatter.setTimeZone(timeZone);

        Calendar calendar =
                Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(timeStamp * 1000);
        String result = dateFormatter.format(calendar.getTime());
        calendar.clear();
        return result;
    }

    private void showCauseDetails(Products products) {
        productPrice.setVisibility(View.GONE);
        subscribeTextView.setVisibility(View.GONE);
        if (products.getCauseTarget() >= products.getCauseDone())
            remainingAmount.setText(String.format(getString(R.string.egp_to_go), String.valueOf(products.getCauseTarget() - products.getCauseDone())));
        else
            remainingAmount.setVisibility(View.GONE);
        causeProgressLayout.setVisibility(View.VISIBLE);
        causeSeekBar.setMax((int) products.getCauseTarget());
        causeSeekBar.setProgress((int) products.getCauseDone());
        causeSeekBar.setEnabled(false);
        causeTargetTextView.setText(String.format(getString(R.string.egp), String.valueOf(products.getCauseTarget())));
        causeCurrentAmount.setText(String.format(getString(R.string.egp), String.valueOf(products.getCauseDone())));
    }

    private void showProductDetails(Products products) {
        causeProgressLayout.setVisibility(View.GONE);
        productPrice.setText(String.format(getString(R.string.egp), String.valueOf(products.getProductPrice())));
//        if (products.isSubscribed()) {
//            donateNowBtn.setText(getString(R.string.subscribe));
//        }
        if (products.getProductTarget() >= products.getProductDone())
            remainingAmount.setText(getResources().getQuantityString(R.plurals.items_plural,
                    (products.getProductTarget() - products.getProductDone()), (products.getProductTarget() - products.getProductDone())));
        else
            remainingAmount.setVisibility(View.GONE);

        if (products.isRegularProduct()) {
            isRegularProduct = true;
            if (products.isSubscribed()) {
                isSubscribed = true;
                subscribeTextView.setText(getString(R.string.view_subscribtion));
            } else {
                isSubscribed = false;
                subscribeTextView.setText(getString(R.string.subscribe));
            }
        }
    }

    private void showCategories(Products products) {
        List<ProductCategory> productCategories = products.getProductCategoryList();
        if (productCategories != null && !productCategories.isEmpty()) {
            String category = "";
            for (int i = 0; i < productCategories.size(); i++) {
                if (i == productCategories.size() - 1) {
                    category += productCategories.get(i).getCategoryName();
                } else {
                    category += productCategories.get(i).getCategoryName() + " / ";
                }
            }
            productCategory.setText(category);
        } else {
            productCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateUI(Products products) {
        String itemType = products.getProductType();
        productDetailsPresenter.sendAnalytics(itemType);

        item = products;
        if (products.isUrgent()) {
            urgentLabel.setVisibility(View.VISIBLE);
        } else {
            urgentLabel.setVisibility(View.GONE);
        }

        if (products.getCreatedAt() == 0) {
            productCreatedAt.setVisibility(View.GONE);
        } else {
            productCreatedAt.setVisibility(View.VISIBLE);
            productCreatedAt.setText(String.format(getString(R.string.published_at), getDate(products.getCreatedAt())));
        }

        if (itemType.equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            showCauseDetails(products);
        } else {
            showProductDetails(products);
        }


        showCategories(products);
        itemUrl = products.getProductUrl();
        if (itemUrl == null || itemUrl.isEmpty()) {
            shareImage.setVisibility(View.INVISIBLE);
        } else {
            shareImage.setVisibility(View.VISIBLE);
        }
        productTitle.setText(products.getProductTitle());
        productDetails.setText(products.getProductDescription());
        productHashTag.setText(products.getProductHashTag());
        imagesList.add(products.getProductImage());
        imagesPagerAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onAddToCartClicked(int quantity) {
//        productDetailsPresenter.addItemToCart(item, quantity);
//        addProductToCartDialog.dismiss();
//        cartAction.onItemInserted();
//    }

    public void setCartAction(CartAction cartAction) {
        this.cartAction = cartAction;
    }

//    @Override
//    public void onAddToCartClicked(double quantity) {
//        productDetailsPresenter.addItemToCart(item, quantity);
//        addCauseToCartDialog.dismiss();
//    }

    @Override
    public void onItemInserted() {
        cartAction.onItemInserted();
    }

    public interface CartAction {
        void onItemInserted();
    }
}
