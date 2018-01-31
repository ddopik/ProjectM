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
import com.spade.mek.ui.login.view.LoginDialogFragment;
import com.spade.mek.ui.more.regular_products.view.SubscribeActivity;
import com.spade.mek.ui.more.regular_products.view.SubscribeFragment;
import com.spade.mek.ui.more.regular_products.view.ViewSubscriptionDialog;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenter;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenterImpl;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.ConstUtil;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsFragment extends BaseFragment implements ProductDetailsView,
        AddProductToCartDialog.AddToCart,
        AddCauseToCartDialog.AddToCart, LoginDialogFragment.LoginDialogActions, ViewSubscriptionDialog.SubscriptionActions {

    public static final String EXTRA_PRODUCT_TYPE = "EXTRA_PRODUCT_TYPE";
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
    public static final String EXTRA_PRODUCT_TITLE = "EXTRA_PRODUCT_TITLE";
    public static final String EXTRA_SUBSCRIPTION_AMOUNT = "EXTRA_SUBSCRIPTION_AMOUNT";
    public static final String EXTRA_SUBSCRIPTION_QUANTITY = "EXTRA_SUBSCRIPTION_QUANTITY";
    public static final String EXTRA_PRODUCT_PRICE = "EXTRA_PRODUCT_PRICE";


    public static final int EXTRA_REGULAR_PRODUCT = 10;
    public static final int EXTRA_NORMAL_PRODUCT = 20;
    public static final int SUBSCRIBE_REQUEST_CODE = 700;
    public static final String ITEM_ID = "ITEM_ID";
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

    private int itemId, productType;
    private String itemUrl = "";
    private CartAction cartAction;
    private boolean isRegular = false, isSubscribed = false;
    private Button donateNowBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getInt(ITEM_ID);
        productType = getArguments().getInt(EXTRA_PRODUCT_TYPE);
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
        ViewPager imagesViewPager = productDetailsView.findViewById(R.id.product_images_view_pager);
        donateNowBtn = productDetailsView.findViewById(R.id.donate_now_btn);
        String appLang = PrefUtils.getAppLang(getContext());

        productTitle = productDetailsView.findViewById(R.id.item_title);
        productCategory = productDetailsView.findViewById(R.id.item_category);
        productDetails = productDetailsView.findViewById(R.id.item_details);
        productPrice = productDetailsView.findViewById(R.id.item_price);
        productCreatedAt = productDetailsView.findViewById(R.id.item_publish_date);
        productHashTag = productDetailsView.findViewById(R.id.item_hash_tag);
        causeTargetTextView = productDetailsView.findViewById(R.id.cause_target);
        causeCurrentAmount = productDetailsView.findViewById(R.id.cause_current_state);
        remainingAmount = productDetailsView.findViewById(R.id.remaining_amount_text_view);
        subscribeTextView = productDetailsView.findViewById(R.id.subscribe_text);
        shareImage = productDetailsView.findViewById(R.id.share_image_view);
        progressBar = productDetailsView.findViewById(R.id.progress_bar);
        causeSeekBar = productDetailsView.findViewById(R.id.cause_target_progress_bar);
        urgentLabel = productDetailsView.findViewById(R.id.urgent_case_label);
        causeProgressLayout = productDetailsView.findViewById(R.id.cause_progress_layout);
        ImageView urgentImageView = productDetailsView.findViewById(R.id.urgent_case_item);
        if (PrefUtils.getAppLang(getContext()).equals(PrefUtils.ARABIC_LANG)) {
            urgentImageView.setImageResource(R.drawable.rotated_small_urgent_image);
        }
        imagesPagerAdapter = new ImagesPagerAdapter(getContext(), imagesList, ImageUtils.getDefaultImage(appLang));
        imagesViewPager.setAdapter(imagesPagerAdapter);

        shareImage.setOnClickListener(v -> productDetailsPresenter.shareItem(itemUrl));
        productDetailsPresenter.getProductDetails(appLang, itemId);

        donateNowBtn.setOnClickListener(v -> {
            if (isRegular) {
                checkSubscription();
            } else {
                showDialogFragment();
            }
        });


        subscribeTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        subscribeTextView.setOnClickListener(v -> checkSubscription());
    }


    private void checkSubscription() {
        if (isSubscribed) {
            showSubscriptionData();
        } else {
            if (PrefUtils.getLoginProvider(getContext()) == LoginProviders.NONE.getLoginProviderCode()) {
                showLoginDialog();
            } else {
                startSubscriptionActivity();
            }
        }
    }

    private void startSubscriptionActivity() {
        Intent intent = SubscribeActivity.getLaunchIntent(getContext());
        intent.putExtra(EXTRA_PRODUCT, item);
        startActivityForResult(intent, SUBSCRIBE_REQUEST_CODE);
    }

    private void showSubscriptionData() {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PRODUCT_TITLE, item.getProductTitle());
        bundle.putString(EXTRA_SUBSCRIPTION_AMOUNT, String.valueOf(item.getSubscriptionData().getTotalAmount()));
        bundle.putString(EXTRA_SUBSCRIPTION_QUANTITY, String.valueOf(item.getSubscriptionData().getQuantity()));
        bundle.putString(EXTRA_PRODUCT_PRICE, String.valueOf(item.getProductPrice()));
        bundle.putString(SubscribeFragment.EXTRA_DURATION, item.getSubscriptionData().getDuration());

        ViewSubscriptionDialog viewSubscriptionDialog = new ViewSubscriptionDialog();
        viewSubscriptionDialog.setArguments(bundle);
        viewSubscriptionDialog.setSubscriptionActions(this);
        viewSubscriptionDialog.show(getChildFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    private void showLoginDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(RegisterActivity.EXTRA_TYPE, RegisterActivity.DEFAULT_TYPE);
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setLoginDialogActions(this);
        loginDialogFragment.setArguments(bundle);
        loginDialogFragment.show(getChildFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    private void showDialogFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ITEM, item);
        //todo A_M [New_task]
        bundle.putString("category_event", ConstUtil.CATEGORY_QUICK_DONATION);
        bundle.putString("category_action", ConstUtil.ACTION_DONATE_BUTTON);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SUBSCRIBE_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
        productDetailsPresenter.getProductDetails(PrefUtils.getAppLang(getContext()), item.getProductId());
//            }
    }

    private void showCauseDetails(Products products) {
        productPrice.setVisibility(View.GONE);
        subscribeTextView.setVisibility(View.GONE);
        donateNowBtn.setText(getString(R.string.donate_now));
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
        if (products.getProductTarget() >= products.getProductDone())
            remainingAmount.setText(getResources().getQuantityString(R.plurals.items_plural,
                    (products.getProductTarget() - products.getProductDone()), (products.getProductTarget() - products.getProductDone())));
        else
            remainingAmount.setVisibility(View.GONE);

        if (productType == EXTRA_REGULAR_PRODUCT) {
            isRegular = true;
            subscribeTextView.setVisibility(View.GONE);
            if (products.isSubscribed()) {
                isSubscribed = true;
                donateNowBtn.setText(getString(R.string.view_subscribtion));
            } else {
                isSubscribed = false;
                donateNowBtn.setText(getString(R.string.subscribe));
            }
        } else {
            donateNowBtn.setText(getString(R.string.donate_now));
            if (products.isRegularProduct()) {
                if (products.isSubscribed()) {
                    isSubscribed = true;
                    subscribeTextView.setText(getString(R.string.view_subscribtion));
                } else {
                    isSubscribed = false;
                    subscribeTextView.setText(getString(R.string.subscribe));
                }
            } else {
                subscribeTextView.setVisibility(View.GONE);
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
        donateNowBtn.setVisibility(View.VISIBLE);
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
            productCreatedAt.setText(String.format(getString(R.string.published_at), TimeUtils.getDate(products.getCreatedAt())));
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

    public void setCartAction(CartAction cartAction) {
        this.cartAction = cartAction;
    }


    @Override
    public void onItemInserted() {
        cartAction.onItemInserted();
    }

    @Override
    public void loginAsGuest() {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onUnSubscribeClicked() {
        productDetailsPresenter.unSubscribeProduct(String.valueOf(item.getProductId()));
    }

    public interface CartAction {
        void onItemInserted();
    }
}
