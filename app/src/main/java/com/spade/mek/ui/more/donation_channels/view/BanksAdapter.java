package com.spade.mek.ui.more.donation_channels.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.donation_channels.model.Bank;
import com.spade.mek.utils.FontUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BanksViewHolder> {

    private List<Bank> bankList;
    private Context mContext;

    public BanksAdapter(List<Bank> bankList, Context mContext) {
        this.bankList = bankList;
        this.mContext = mContext;
    }

    @Override
    public BanksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.bank_item, parent, false);
        return new BanksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BanksViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        if (bank.getBankName() != null && !bank.getBankName().isEmpty()) {
            holder.bankTitle.setText(bank.getBankName());
        }
        CategoriesAdapter bankCategoriesAdapter = new CategoriesAdapter(mContext, bank.getBankCategoryList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.categoriesRecyclerView.setAdapter(bankCategoriesAdapter);
        holder.categoriesRecyclerView.setLayoutManager(layoutManager);
        FontUtils.overrideFonts(mContext, holder.itemView);
    }


    @Override
    public int getItemCount() {
        return bankList.size();
    }

    class BanksViewHolder extends RecyclerView.ViewHolder {
        TextView bankTitle;
        RecyclerView categoriesRecyclerView;

        public BanksViewHolder(View itemView) {
            super(itemView);
            bankTitle = (TextView) itemView.findViewById(R.id.header_title);
            categoriesRecyclerView = (RecyclerView) itemView.findViewById(R.id.categories_recycler_view);
        }
    }
}
