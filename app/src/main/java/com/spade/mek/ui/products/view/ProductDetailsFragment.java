package com.spade.mek.ui.products.view;

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
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.ProductCategory;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenter;
import com.spade.mek.ui.products.presenter.ProductDetailsPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsFragment extends BaseFragment implements ProductDetailsView {
    public static final String ITEM_ID = "ITEM_ID";
    private View productDetailsView;
    private TextView productTitle, productCategory, productDetails,
            productCreatedAt, productPrice, productHashTag, remainingAmount,
            causeTargetTextView, causeCurrentAmount;
    private FrameLayout urgentLabel;
    private ImageView shareImage;
    private RelativeLayout causeProgressLayout;
    private Button donateNowBtn;
    private ProductDetailsPresenter productDetailsPresenter;
    private int itemId;
    private String itemUrl = "";

    private List<String> imagesList;
    private ProgressBar progressBar;
    private ImagesPagerAdapter imagesPagerAdapter;
    private SeekBar causeSeekBar;


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
        return productDetailsView;
    }

    @Override
    protected void initPresenter() {
        productDetailsPresenter = new ProductDetailsPresenterImpl(getContext());
        productDetailsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        imagesList = new ArrayList<>();

        productTitle = (TextView) productDetailsView.findViewById(R.id.item_title);
        productCategory = (TextView) productDetailsView.findViewById(R.id.item_category);
        productDetails = (TextView) productDetailsView.findViewById(R.id.item_details);
        productPrice = (TextView) productDetailsView.findViewById(R.id.item_price);
        productCreatedAt = (TextView) productDetailsView.findViewById(R.id.item_publish_date);
        productHashTag = (TextView) productDetailsView.findViewById(R.id.item_hash_tag);
        urgentLabel = (FrameLayout) productDetailsView.findViewById(R.id.urgent_case_label);
        causeTargetTextView = (TextView) productDetailsView.findViewById(R.id.cause_target);
        causeCurrentAmount = (TextView) productDetailsView.findViewById(R.id.cause_current_state);
        remainingAmount = (TextView) productDetailsView.findViewById(R.id.remaining_amount_text_view);
        ViewPager imagesViewPager = (ViewPager) productDetailsView.findViewById(R.id.product_images_view_pager);
        shareImage = (ImageView) productDetailsView.findViewById(R.id.share_image_view);
        donateNowBtn = (Button) productDetailsView.findViewById(R.id.donate_now_btn);
        causeProgressLayout = (RelativeLayout) productDetailsView.findViewById(R.id.cause_progress_layout);
        progressBar = (ProgressBar) productDetailsView.findViewById(R.id.progress_bar);
        causeSeekBar = (SeekBar) productDetailsView.findViewById(R.id.cause_target_progress_bar);

        imagesPagerAdapter = new ImagesPagerAdapter(getContext(), imagesList, ImageUtils.getDefaultImage(appLang));
        imagesViewPager.setAdapter(imagesPagerAdapter);

        shareImage.setOnClickListener(v -> productDetailsPresenter.shareItem(itemUrl));
        productDetailsPresenter.getProductDetails(appLang, itemId);

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
    public void updateUI(Products products) {
        String itemType = products.getProductType();
        if (products.isUrgent()) {
            urgentLabel.setVisibility(View.VISIBLE);
        } else {
            urgentLabel.setVisibility(View.GONE);
        }

        if (itemType.equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
//            if (products.getCreatedAt() == null || products.getCreatedAt().equals("null")) {
//                productCreatedAt.setVisibility(View.GONE);
//            } else {
//                productCreatedAt.setText(String.format(getString(R.string.published_at), products.getCreatedAt()));
//            }
            productPrice.setVisibility(View.GONE);
            remainingAmount.setText(String.format(getString(R.string.egp_to_go), String.valueOf(products.getCauseTarget() - products.getCauseDone())));
            causeSeekBar.setMax((int) products.getCauseTarget());
            causeSeekBar.setProgress((int) products.getCauseDone());
            causeSeekBar.setEnabled(false);
            causeTargetTextView.setText(String.format(getString(R.string.egp), String.valueOf(products.getCauseTarget())));
            causeCurrentAmount.setText(String.format(getString(R.string.egp), String.valueOf(products.getCauseDone())));

        } else {
            productCreatedAt.setVisibility(View.GONE);
            causeProgressLayout.setVisibility(View.GONE);
            productPrice.setText(String.format(getString(R.string.egp), String.valueOf(products.getProductPrice())));
            remainingAmount.setText(String.format(getString(R.string.item_to_go), String.valueOf(products.getProductTarget() - products.getProductDone())));
        }

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
        itemUrl = products.getProductUrl();
        if (itemUrl == null || itemUrl.isEmpty()) {
            shareImage.setVisibility(View.GONE);
        } else {
            shareImage.setVisibility(View.VISIBLE);
        }
        productTitle.setText(products.getProductTitle());
        productDetails.setText(products.getProductDescription());
        productHashTag.setText(products.getProductHashTag());
        imagesList.add(products.getProductImage());
        imagesPagerAdapter.notifyDataSetChanged();
    }
}
