package com.spade.mek.ui.home.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.spade.mek.R;
import com.spade.mek.ui.home.products.Products;

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
        TextView caseTitle = (TextView) itemView.findViewById(R.id.case_title);
        TextView urgentCaseLabel = (TextView) itemView.findViewById(R.id.urgent_case_label);
        ImageView actionImage = (ImageView) itemView.findViewById(R.id.action_image_view);
        ImageView shareImage = (ImageView) itemView.findViewById(R.id.share_image_view);
        ANImageView caseImage = (ANImageView) itemView.findViewById(R.id.case_image_view);
        FrameLayout caseLayout = (FrameLayout) itemView.findViewById(R.id.case_layout);

        caseImage.setDefaultImageResId(defaultImageResId);
        caseImage.setErrorImageResId(defaultImageResId);
        caseImage.setImageUrl(urgentCase.getProductImage());
        caseLayout.setOnClickListener(v -> onCaseClicked.onCaseClicked(urgentCase.getProductId(), urgentCase.getProductType()));
        shareImage.setOnClickListener(v -> onCaseClicked.onShareClicked(urgentCase.getProductUrl()));
        if (urgentCase.getProductUrl() == null || urgentCase.getProductUrl().isEmpty()) {
            shareImage.setVisibility(View.GONE);
        } else {
            shareImage.setVisibility(View.VISIBLE);
        }
        caseTitle.setText(urgentCase.getProductTitle());

        if (urgentCase.isUrgent()) {
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
    }
}
