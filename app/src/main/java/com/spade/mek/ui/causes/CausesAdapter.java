package com.spade.mek.ui.causes;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.search.SearchActivity;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;
import com.spade.mek.utils.PrefUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class CausesAdapter extends RecyclerView.Adapter implements UrgentCasesPagerAdapter.OnCaseClicked {

    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    private Context mContext;
    private List<Products> latestCausesList;
    private List<Products> urgentCaseList;
    private int defaultDrawableResId;
    private CausesAction productActions;
    private String title;
    private int viewType;


    public CausesAdapter(Context context, List<Products> latestCausesList, List<Products> urgentCaseList, String title, int viewType, int defaultDrawableResId) {
        this.mContext = context;
        this.latestCausesList = latestCausesList;
        this.urgentCaseList = urgentCaseList;
        this.defaultDrawableResId = defaultDrawableResId;
        this.title = title;
        this.viewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == HEADER_TYPE) {
            return new HeaderViewHolder(inflater.inflate(R.layout.urgent_cases_layout, parent, false));
        } else {
            return new ItemViewHolder(inflater.inflate(R.layout.cause_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Products latestCause = latestCausesList.get(position - 1);
            ((ItemViewHolder) holder).causeSeekBar.setMax((int) latestCause.getCauseTarget());
            ((ItemViewHolder) holder).causeSeekBar.setProgress((int) latestCause.getCauseDone());
            ((ItemViewHolder) holder).causeSeekBar.setEnabled(false);
            ((ItemViewHolder) holder).causeTitle.setText(latestCause.getProductTitle());
            ((ItemViewHolder) holder).causeTargetTextView.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseTarget())));
            ((ItemViewHolder) holder).causeCurrentAmount.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseDone())));

            VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
            GlideApp.with(mContext).load(latestCause.getProductImage()).centerCrop().
                    placeholder(defaultDrawable).error(defaultDrawable).into(((ItemViewHolder) holder).causeImage);
            ((ItemViewHolder) holder).itemView.setOnClickListener(v -> productActions.onCauseClicked(latestCause.getProductId()));
            ((ItemViewHolder) holder).shareImageView.setOnClickListener(v -> productActions.onShareClicked(latestCause.getProductUrl()));
            ((ItemViewHolder) holder).donateImage.setOnClickListener(v -> productActions.onDonateCauseClicked(latestCause));
            if (latestCause.getProductUrl() == null || latestCause.getProductUrl().isEmpty()) {
                ((ItemViewHolder) holder).shareImageView.setVisibility(View.GONE);
            } else {
                ((ItemViewHolder) holder).shareImageView.setVisibility(View.VISIBLE);
            }
            if (latestCause.isUrgent()) {
                if (PrefUtils.getAppLang(mContext).equals(PrefUtils.ARABIC_LANG)) {
                    ((ItemViewHolder) holder).isUrgentImageView.setRotationY(180);
                }
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).isUrgentImageView.setVisibility(View.GONE);
            }

        } else if (holder instanceof HeaderViewHolder) {
            if (urgentCaseList.isEmpty()) {
                ((HeaderViewHolder) holder).casesViewPager.setVisibility(View.GONE);
            } else {
                ((HeaderViewHolder) holder).casesViewPager.setVisibility(View.VISIBLE);
                UrgentCasesPagerAdapter urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(mContext, urgentCaseList, defaultDrawableResId);
                urgentCasesPagerAdapter.setOnCaseClicked(this);
                ((HeaderViewHolder) holder).casesViewPager.setAdapter(urgentCasesPagerAdapter);
            }
            if (viewType == SearchActivity.SEARCH_VIEW) {
                ((HeaderViewHolder) holder).title.setVisibility(View.GONE);
            } else {
                ((HeaderViewHolder) holder).title.setText(title);
            }
        }
        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return latestCausesList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_TYPE;
        return ITEM_TYPE;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setProductActions(CausesAction productActions) {
        this.productActions = productActions;
    }

    @Override
    public void onCaseClicked(int id, String productType) {
        productActions.onCauseClicked(id);
    }

    @Override
    public void onShareClicked(String url) {
        productActions.onShareClicked(url);
    }

    @Override
    public void onActionClicked(Products product) {
        productActions.onDonateCauseClicked(product);
    }

    public interface CausesAction {
        void onCauseClicked(int productId);

        void onShareClicked(String url);

        void onDonateCauseClicked(Products cause);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView causeTargetTextView, causeCurrentAmount, causeTitle;
        private ImageView shareImageView;
        private ImageView causeImage;
        private ImageView donateImage;
        private ImageView isUrgentImageView;
        private SeekBar causeSeekBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            causeTargetTextView = (TextView) itemView.findViewById(R.id.cause_target);
            causeCurrentAmount = (TextView) itemView.findViewById(R.id.cause_current_state);
            causeTitle = (TextView) itemView.findViewById(R.id.cause_title);
            causeImage = (ImageView) itemView.findViewById(R.id.cause_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
            causeSeekBar = (SeekBar) itemView.findViewById(R.id.cause_target_progress_bar);
            donateImage = (ImageView) itemView.findViewById(R.id.donate_image_view);
            isUrgentImageView = (ImageView) itemView.findViewById(R.id.is_urgent_image_view);
        }

    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ViewPager casesViewPager;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            casesViewPager = (ViewPager) itemView.findViewById(R.id.urgent_cases_view_pager);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
