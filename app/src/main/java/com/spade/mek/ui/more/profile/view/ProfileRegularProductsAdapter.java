package com.spade.mek.ui.more.profile.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProfileRegularProductsAdapter extends RecyclerView.Adapter<ProfileRegularProductsAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Products> productsList;
    private int defaultDrawableResId;
    private ProductActions productActions;

    public ProfileRegularProductsAdapter(List<Products> productsList, int defaultResId, Context context) {
        this.mContext = context;
        this.productsList = productsList;
        this.defaultDrawableResId = defaultResId;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ItemViewHolder(inflater.inflate(R.layout.profile_regular_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.productTitle.setText(products.getProductTitle());
        holder.productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(products.getProductPrice()))
                + " | " +
                String.format(mContext.getString(R.string.month_reminder), products.getSubscriptionData().getDuration()));

        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(products.getProductImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(holder.productImage);

        holder.itemView.setOnClickListener(v -> productActions.onProductClicked(products));
        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setProductActions(ProductActions productActions) {
        this.productActions = productActions;
    }

    public interface ProductActions {
        void onProductClicked(Products product);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productPrice;
        private ImageView productImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price_and_reminder);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);

        }
    }
}
