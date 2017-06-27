package com.spade.mek.ui.cart.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context mContext;
    private int defaultDrawableResId;
    private CartActions cartActions;

    public CartAdapter(List<CartItem> cartItemList, Context mContext, int defaultDrawableResId) {
        this.cartItemList = cartItemList;
        this.mContext = mContext;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.titleTextView.setText(cartItem.getItemTitle());
        if (cartItem.getItemType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            holder.categoryTextView.setText(mContext.getString(R.string.title_cause));
            holder.totalPriceTextView.setText(mContext.getString(R.string.egp_text));
            holder.moneyAmountEditText.setText(String.valueOf(cartItem.getMoneyAmount()));
        } else {
            holder.categoryTextView.setText(mContext.getString(R.string.title_product));
            holder.totalPriceTextView.setText(
                    String.format(mContext.getString(R.string.egp), String.valueOf(cartItem.getAmount() * cartItem.getItemPrice())));
            holder.moneyAmountEditText.setText(String.valueOf(cartItem.getAmount()));

        }
        holder.moneyAmountEditText.setEnabled(false);
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(cartItem.getItemImage()).
                centerCrop().placeholder(defaultDrawable).into(holder.itemImage);
        holder.increaseImage.setOnClickListener(v -> cartActions.onIncreaseClicked(cartItem,position));
        holder.decreaseImage.setOnClickListener(v -> cartActions.onDecreaseClicked(cartItem,position));
        holder.closeImage.setOnClickListener(v -> cartActions.onClearClicked(cartItem,position));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void setCartActions(CartActions cartActions) {
        this.cartActions = cartActions;
    }

    public interface CartActions {
        void onIncreaseClicked(CartItem cartItem,int position);

        void onDecreaseClicked(CartItem cartItem,int position);

        void onClearClicked(CartItem cartItem,int position);

    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, categoryTextView, totalPriceTextView;
        private ImageView itemImage, increaseImage, decreaseImage, closeImage;
        private EditText moneyAmountEditText;

        public CartViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.item_title);
            categoryTextView = (TextView) itemView.findViewById(R.id.item_category);
            totalPriceTextView = (TextView) itemView.findViewById(R.id.total_item_price);
            moneyAmountEditText = (EditText) itemView.findViewById(R.id.quantityEditText);
            itemImage = (ImageView) itemView.findViewById(R.id.cart_item_image);
            increaseImage = (ImageView) itemView.findViewById(R.id.arrow_up);
            decreaseImage = (ImageView) itemView.findViewById(R.id.arrow_down);
            closeImage = (ImageView) itemView.findViewById(R.id.close_btn);

        }
    }
}
