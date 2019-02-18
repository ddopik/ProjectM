package com.spade.mek.ui.more.donation_channels.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.donation_channels.model.BankCategory;
import com.spade.mek.utils.FontUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/20/17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private Context mContext;
    private List<BankCategory> bankCategories;

    public CategoriesAdapter(Context mContext, List<BankCategory> bankCategories) {
        this.mContext = mContext;
        this.bankCategories = bankCategories;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.bank_category_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        BankCategory bankCategory = bankCategories.get(position);
        holder.categoryTitle.setText(bankCategory.getCategoryName());
        holder.currencyTitle.setText(String.format(mContext.getString(R.string.currency), bankCategory.getCurrency()));
        holder.swiftCode.setText(String.format(mContext.getString(R.string.swift), bankCategory.getSwiftCodes().get(0), bankCategory.getBankAccount()));
        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return bankCategories.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle, swiftCode, currencyTitle;

        public CategoriesViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.category_title);
            swiftCode = (TextView) itemView.findViewById(R.id.swift_code);
            currencyTitle = (TextView) itemView.findViewById(R.id.currency_title);
        }
    }
}
