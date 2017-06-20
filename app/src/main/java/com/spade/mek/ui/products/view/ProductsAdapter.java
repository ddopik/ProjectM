package com.spade.mek.ui.products.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.spade.mek.R;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter {
    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    private Context mContext;
    private List<Products> productsList;
    private List<Products> urgentCaseList;
    private int defaultDrawableResId;
    private ProductActions productActions;

    public ProductsAdapter(List<Products> productsList, List<Products> urgentCaseList, int defaultResId, Context context) {
        this.mContext = context;
        this.productsList = productsList;
        this.urgentCaseList = urgentCaseList;
        this.defaultDrawableResId = defaultResId;
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
            ((ItemViewHolder) holder).productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestProducts.getProductDone())));
            ((ItemViewHolder) holder).productImage.setDefaultImageResId(defaultDrawableResId);
            ((ItemViewHolder) holder).productImage.setErrorImageResId(defaultDrawableResId);
            ((ItemViewHolder) holder).productImage.setImageUrl(latestProducts.getProductImage());
            ((ItemViewHolder) holder).itemView.setOnClickListener(v -> productActions.onProductClicked(latestProducts.getProductId()));
            if (latestProducts.isUrgent()) {
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.GONE);
            }
        } else if (holder instanceof HeaderViewHolder) {
            UrgentCasesPagerAdapter urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(mContext, urgentCaseList, defaultDrawableResId);
            ((HeaderViewHolder) holder).casesViewPager.setAdapter(urgentCasesPagerAdapter);
        }
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

    public interface ProductActions {
        void onProductClicked(int productId);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productPrice;
        private ImageView shareImageView, isUrgentImageView;
        private ANImageView productImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productImage = (ANImageView) itemView.findViewById(R.id.product_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
            isUrgentImageView = (ImageView) itemView.findViewById(R.id.is_urgent_image_view);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        ViewPager casesViewPager;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            casesViewPager = (ViewPager) itemView.findViewById(R.id.urgent_cases_view_pager);
        }
    }
}
