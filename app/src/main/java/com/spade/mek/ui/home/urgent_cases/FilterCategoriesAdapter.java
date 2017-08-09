package com.spade.mek.ui.home.urgent_cases;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.FilterCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/3/17.
 */

public class FilterCategoriesAdapter extends RecyclerView.Adapter<FilterCategoriesAdapter.FilterCategoriesViewHolder> {

    private List<FilterCategory> filterCategories;
    private Context mContext;
    private OnFilterCategoryClicked onFilterCategoryClicked;
    private List<String> selectedFilters = new ArrayList<>();

    public FilterCategoriesAdapter(List<FilterCategory> filterCategories, Context mContext) {
        this.filterCategories = filterCategories;
        this.mContext = mContext;
    }

    @Override
    public FilterCategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.filter_category_item, parent, false);
        return new FilterCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterCategoriesViewHolder holder, int position) {
        FilterCategory filterCategory = filterCategories.get(position);
        holder.filterCategory.setText(filterCategory.getCategoryName());
        if (!selectedFilters.contains(filterCategory.getCategoryName())) {
            holder.filterCategory.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } else {
            holder.filterCategory.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }

        holder.filterCategory.setOnClickListener(v -> {
            if (selectedFilters.contains(filterCategory.getCategoryName())) {
                selectedFilters.remove(filterCategory.getCategoryName());
                holder.filterCategory.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            } else {
                selectedFilters.add(filterCategory.getCategoryName());
                holder.filterCategory.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            }
            onFilterCategoryClicked.onFilterClicked(filterCategory);
        });
    }

    public void setOnFilterCategoryClicked(OnFilterCategoryClicked onFilterCategoryClicked) {
        this.onFilterCategoryClicked = onFilterCategoryClicked;
    }

    public interface OnFilterCategoryClicked {
        void onFilterClicked(FilterCategory filterCategory);
    }

    public void clearFilters() {
        selectedFilters.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filterCategories.size();
    }

    class FilterCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView filterCategory;

        FilterCategoriesViewHolder(View itemView) {
            super(itemView);
            filterCategory = (TextView) itemView.findViewById(R.id.filter_category);
        }
    }
}
