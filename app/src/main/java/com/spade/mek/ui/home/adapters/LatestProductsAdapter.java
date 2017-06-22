package com.spade.mek.ui.home.adapters;

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
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class LatestProductsAdapter extends RecyclerView.Adapter<LatestProductsAdapter.LatestCausesViewHolder> {

    private Context mContext;
    private List<Products> latestProductsList;
    private int defaultDrawableResId;
    private OnProductClicked onProductClicked;

    public LatestProductsAdapter(Context context, List<Products> latestProductsList, int defaultDrawableResId) {
        this.mContext = context;
        this.latestProductsList = latestProductsList;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    public LatestCausesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_product_item, parent, false);
        return new LatestCausesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LatestCausesViewHolder holder, int position) {
        Products latestProducts = latestProductsList.get(position);
        holder.productTitle.setText(latestProducts.getProductTitle());
        holder.productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestProducts.getProductPrice())));
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(latestProducts.getProductImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(holder.productImage);

//        holder.productImage.setDefaultImageResId(defaultDrawableResId);
//        holder.productImage.setErrorImageResId(defaultDrawableResId);
//        holder.productImage.setImageUrl(latestProducts.getProductImage());

        holder.itemView.setOnClickListener(v -> onProductClicked.onProductClicked(latestProducts.getProductId()));
        holder.shareImageView.setOnClickListener(v -> onProductClicked.onShareClicked(latestProducts.getProductUrl()));
        if (latestProducts.getProductUrl() == null || latestProducts.getProductUrl().isEmpty()) {
            holder.shareImageView.setVisibility(View.GONE);
        } else {
            holder.shareImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return latestProductsList.size();
    }

    public void setOnProductClicked(OnProductClicked onProductClicked) {
        this.onProductClicked = onProductClicked;
    }

    public interface OnProductClicked {
        void onProductClicked(int id);

        void onShareClicked(String url);
    }

    public class LatestCausesViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productPrice;
        private ImageView shareImageView;
        private ImageView productImage;


        public LatestCausesViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);

        }
    }
}
