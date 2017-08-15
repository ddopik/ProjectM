package com.spade.mek.ui.products.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;
import com.spade.mek.utils.PrefUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter implements UrgentCasesPagerAdapter.OnCaseClicked {
    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    private Context mContext;
    private List<Products> productsList;
    private List<Products> urgentCaseList;
    private int defaultDrawableResId;
    private ProductActions productActions;
    private String title;

    public ProductsAdapter(List<Products> productsList, List<Products> urgentCaseList, int defaultResId, String title, Context context) {
        this.mContext = context;
        this.productsList = productsList;
        this.urgentCaseList = urgentCaseList;
        this.defaultDrawableResId = defaultResId;
        this.title = title;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == HEADER_TYPE) {
            return new HeaderViewHolder(inflater.inflate(R.layout.urgent_cases_layout, parent, false));
        } else {
            return new ItemViewHolder(inflater.inflate(R.layout.grid_product_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Products latestProducts = productsList.get(position - 1);
            ((ItemViewHolder) holder).productTitle.setText(latestProducts.getProductTitle());
            ((ItemViewHolder) holder).productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestProducts.getProductPrice())));

            VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
            GlideApp.with(mContext).load(latestProducts.getProductImage()).centerCrop().
                    placeholder(defaultDrawable).error(defaultDrawable).into(((ItemViewHolder) holder).productImage);

            ((ItemViewHolder) holder).itemView.setOnClickListener(v -> productActions.onProductClicked(latestProducts.getProductId()));
            ((ItemViewHolder) holder).shareImageView.setOnClickListener(v -> productActions.onShareClicked(latestProducts.getProductUrl()));
            ((ItemViewHolder) holder).addToCartImageView.setOnClickListener(v -> productActions.onAddToCartClicked(latestProducts));

            if (latestProducts.isUrgent()) {
                if (PrefUtils.getAppLang(mContext).equals(PrefUtils.ARABIC_LANG)) {
                    ((ItemViewHolder) holder).isUrgentImageView.setRotationY(180);
                }
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.GONE);
            }
            if (latestProducts.getProductUrl() == null || latestProducts.getProductUrl().isEmpty()) {
                ((ItemViewHolder) holder).shareImageView.setVisibility(View.GONE);
            } else {
                ((ItemViewHolder) holder).shareImageView.setVisibility(View.VISIBLE);
            }
        } else if (holder instanceof HeaderViewHolder) {
            if (urgentCaseList.isEmpty()) {
                ((HeaderViewHolder) holder).casesViewPager.setVisibility(View.GONE);
            } else {
                ((HeaderViewHolder) holder).casesViewPager.setVisibility(View.VISIBLE);
                UrgentCasesPagerAdapter urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(mContext, urgentCaseList, defaultDrawableResId);
                urgentCasesPagerAdapter.setOnCaseClicked(this);
                ((HeaderViewHolder) holder).casesViewPager.setAdapter(urgentCasesPagerAdapter);
            }
            ((HeaderViewHolder) holder).title.setText(title);
        }

        FontUtils.overrideFonts(mContext, holder.itemView);
    }


    @Override
    public int getItemCount() {
        return productsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_TYPE;
        return ITEM_TYPE;
    }


    public void setProductActions(ProductActions productActions) {
        this.productActions = productActions;
    }

    @Override
    public void onCaseClicked(int id, String productType) {
        productActions.onProductClicked(id);
    }

    @Override
    public void onShareClicked(String url) {
        productActions.onShareClicked(url);
    }

    @Override
    public void onActionClicked(Products product) {
        productActions.onAddToCartClicked(product);
    }

    public interface ProductActions {
        void onProductClicked(int productId);

        void onShareClicked(String url);

        void onAddToCartClicked(Products product);

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productPrice;
        private ImageView shareImageView, isUrgentImageView;
        private ImageView productImage;
        private ImageView addToCartImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
            isUrgentImageView = (ImageView) itemView.findViewById(R.id.is_urgent_image_view);
            addToCartImageView = (ImageView) itemView.findViewById(R.id.add_to_cart_image_view);

        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        ViewPager casesViewPager;
        TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            casesViewPager = (ViewPager) itemView.findViewById(R.id.urgent_cases_view_pager);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
