package com.spade.mek.ui.more.regular_products.view;

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

public class RegularProductsAdapter extends RecyclerView.Adapter<RegularProductsAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Products> productsList;
    private int defaultDrawableResId;
    private ProductActions productActions;

    public RegularProductsAdapter(List<Products> productsList, int defaultResId, Context context) {
        this.mContext = context;
        this.productsList = productsList;
        this.defaultDrawableResId = defaultResId;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ItemViewHolder(inflater.inflate(R.layout.regular_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Products latestProducts = productsList.get(position);
        holder.productTitle.setText(latestProducts.getProductTitle());
        holder.productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestProducts.getProductPrice())));

        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(latestProducts.getProductImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(holder.productImage);

        holder.itemView.setOnClickListener(v -> productActions.onProductClicked(latestProducts.getProductId()));

//        if (latestProducts.isUrgent()) {
//            ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.VISIBLE);
//        } else {
//            ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.GONE);
//        }
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
        void onProductClicked(int productId);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle, productPrice, isSubscribed;
        private ImageView productImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            isSubscribed = (TextView) itemView.findViewById(R.id.is_subscribed);

        }
    }
}
