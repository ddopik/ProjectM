package com.spade.mek.ui.home.adapters;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;
import com.spade.mek.utils.PrefUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class UrgentCasesPagerAdapter extends PagerAdapter {
    private List<Products> urgentCaseList;
    private Context mContext;
    private int defaultImageResId;
    public static final String CAUSE_TYPE = "cause";
    public static final String PRODUCT_TYPE = "product";
    public static final String NEWS_TYPE = "news";
    private OnCaseClicked onCaseClicked;


    public UrgentCasesPagerAdapter(Context context, List<Products> urgentCaseList, int defaultImageResId) {
        mContext = context;
        this.defaultImageResId = defaultImageResId;
        this.urgentCaseList = urgentCaseList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Products urgentCase = urgentCaseList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.urgent_case_item, container,
                false);
        TextView caseTitle = itemView.findViewById(R.id.case_title);
        FrameLayout urgentCaseLabel = itemView.findViewById(R.id.urgent_case_label);
        ImageView actionImage = itemView.findViewById(R.id.action_image_view);
        ImageView shareImage = itemView.findViewById(R.id.share_image_view);
        ImageView caseImage = itemView.findViewById(R.id.case_image_view);
        ImageView isUrgentImage = itemView.findViewById(R.id.ic_urgent_label);
        FrameLayout caseLayout = itemView.findViewById(R.id.case_layout);

        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultImageResId, null);
        GlideApp.with(mContext).load(urgentCase.getProductImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(caseImage);

        caseLayout.setOnClickListener(v -> onCaseClicked.onCaseClicked(urgentCase.getProductId(), urgentCase.getProductType()));
        shareImage.setOnClickListener(v -> onCaseClicked.onShareClicked(urgentCase.getProductUrl()));
        actionImage.setOnClickListener(v -> onCaseClicked.onActionClicked(urgentCase));
        if (urgentCase.getProductUrl() == null || urgentCase.getProductUrl().isEmpty()) {
            shareImage.setVisibility(View.GONE);
        } else {
            shareImage.setVisibility(View.VISIBLE);
        }
        caseTitle.setText(urgentCase.getProductTitle());

        if (urgentCase.isUrgent()) {
            if (PrefUtils.getAppLang(mContext).equals(PrefUtils.ARABIC_LANG)) {
                isUrgentImage.setImageResource(R.drawable.rotated_large_urgent_image);
            }
            urgentCaseLabel.setVisibility(View.VISIBLE);
        } else {
            urgentCaseLabel.setVisibility(View.GONE);
        }

        if (urgentCase.getProductType().equals(PRODUCT_TYPE)) {
            actionImage.setImageResource(R.drawable.ic_add_to_cart);
        } else if (urgentCase.getProductType().equals(CAUSE_TYPE)) {
            actionImage.setImageResource(R.drawable.ic_donate);
        }

        container.addView(itemView);
        FontUtils.overrideFonts(mContext, itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return urgentCaseList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnCaseClicked(OnCaseClicked onCaseClicked) {
        this.onCaseClicked = onCaseClicked;
    }

    public interface OnCaseClicked {
        void onCaseClicked(int id, String productType);

        void onShareClicked(String url);

        void onActionClicked(Products product);
    }
}
