package com.spade.mek.ui.more.profile.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.profile.model.Payment;
import com.spade.mek.utils.TimeUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/28/17.
 */

public class PaymentProfileAdapter extends RecyclerView.Adapter<PaymentProfileAdapter.PaymentViewHolder> {

    private Context mContext;
    private List<Payment> paymentList;

    public PaymentProfileAdapter(Context mContext, List<Payment> paymentList) {
        this.mContext = mContext;
        this.paymentList = paymentList;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.profile_payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.typeOfDonation.setText(payment.getTypeOfDonation());
        if (payment.getItemsCount() > 0) {
            holder.itemsNumber.setVisibility(View.VISIBLE);
            holder.itemsNumber.setText(mContext.getResources().getQuantityString(R.plurals.payment_items_plural,
                    payment.getItemsCount(), payment.getItemsCount()));
        } else {
            holder.itemsNumber.setVisibility(View.GONE);
        }
        holder.paymentAmount.setText(String.format(mContext.getString(R.string.egp), String.valueOf(payment.getAmount())));
        holder.paymentDate.setText(TimeUtils.getDate(payment.getCreatedAt()));
        if (payment.getProductsList() != null && !payment.getProductsList().isEmpty()) {
            holder.seeMore.setVisibility(View.VISIBLE);
        } else {
            holder.seeMore.setVisibility(View.GONE);
        }

        holder.seeMore.setOnClickListener(v -> {
            holder.productsLayout.setVisibility(View.VISIBLE);
            holder.seeMore.setVisibility(View.GONE);
        });

        holder.seeLess.setOnClickListener(v -> {
            holder.productsLayout.setVisibility(View.GONE);
            holder.seeMore.setVisibility(View.VISIBLE);
        });

        if (!payment.getProductsList().isEmpty()) {
            PaymentHistoryProductsAdapter paymentHistoryProductsAdapter = new PaymentHistoryProductsAdapter(mContext, payment.getProductsList());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            holder.productsRecycler.setLayoutManager(layoutManager);
            holder.productsRecycler.setAdapter(paymentHistoryProductsAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        RecyclerView productsRecycler;
        TextView paymentDate, itemsNumber, paymentAmount, typeOfDonation, seeMore, seeLess;
        LinearLayout productsLayout;

        public PaymentViewHolder(View itemView) {
            super(itemView);

            paymentAmount = (TextView) itemView.findViewById(R.id.payment_amount);
            paymentDate = (TextView) itemView.findViewById(R.id.payment_date);
            itemsNumber = (TextView) itemView.findViewById(R.id.items_number);
            typeOfDonation = (TextView) itemView.findViewById(R.id.type_of_donation);
            seeMore = (TextView) itemView.findViewById(R.id.see_more);
            seeLess = (TextView) itemView.findViewById(R.id.see_less);
            productsRecycler = (RecyclerView) itemView.findViewById(R.id.products_recycler_view);
            productsLayout = (LinearLayout) itemView.findViewById(R.id.products_layout);
        }
    }
}
