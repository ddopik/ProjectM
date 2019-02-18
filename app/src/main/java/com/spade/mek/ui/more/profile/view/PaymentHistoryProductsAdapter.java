package com.spade.mek.ui.more.profile.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public class PaymentHistoryProductsAdapter extends RecyclerView.Adapter<PaymentHistoryProductsAdapter.PaymentProductsViewHolder> {

    private Context mContext;
    private List<Products> productsList;

    public PaymentHistoryProductsAdapter(Context mContext, List<Products> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;
    }

    @Override
    public PaymentProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.payment_product_item, parent, false);
        return new PaymentProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentProductsViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.productType.setText(products.getProductType());
        holder.productTitle.setText(products.getProductTitle());
        if (products.getProductType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            holder.productType.setText(mContext.getString(R.string.title_cause));
            holder.productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(products.getCausePrice())));
        } else {
            holder.productType.setText(mContext.getString(R.string.title_product));
            holder.productPrice.setText(String.format(mContext.getString(R.string.egp), String.valueOf(products.getProductPrice())));
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class PaymentProductsViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, productType, productPrice;

        public PaymentProductsViewHolder(View itemView) {
            super(itemView);
            productTitle = (TextView) itemView.findViewById(R.id.product_title);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productType = (TextView) itemView.findViewById(R.id.product_type);
        }
    }
}
