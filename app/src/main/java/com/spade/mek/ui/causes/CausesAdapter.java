package com.spade.mek.ui.causes;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.spade.mek.R;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class CausesAdapter extends RecyclerView.Adapter {

    public static final int HEADER_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    private Context mContext;
    private List<Products> latestCausesList;
    private List<Products> urgentCaseList;
    private int defaultDrawableResId;
    private int p;
    private CausesAction productActions;


    public CausesAdapter(Context context, List<Products> latestCausesList, List<Products> urgentCaseList, int defaultDrawableResId) {
        this.mContext = context;
        this.latestCausesList = latestCausesList;
        this.urgentCaseList = urgentCaseList;
        this.defaultDrawableResId = defaultDrawableResId;
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
//        holder.causeSeekBar.setMax(10);
            ((ItemViewHolder) holder).causeSeekBar.setProgress((int) latestCause.getCauseDone());
//        animate(holder.causeSeekBar, 5, 10);
            ((ItemViewHolder) holder).causeSeekBar.setEnabled(false);
            ((ItemViewHolder) holder).causeTitle.setText(latestCause.getProductTitle());
            ((ItemViewHolder) holder).causeTargetTextView.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseTarget())));
            ((ItemViewHolder) holder).causeCurrentAmount.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseDone())));
            ((ItemViewHolder) holder).causeImage.setDefaultImageResId(defaultDrawableResId);
            ((ItemViewHolder) holder).causeImage.setErrorImageResId(defaultDrawableResId);
            ((ItemViewHolder) holder).causeImage.setImageUrl(latestCause.getProductImage());
            ((ItemViewHolder) holder).itemView.setOnClickListener(v -> productActions.onCauseClicked(latestCause.getProductId()));

        } else if (holder instanceof HeaderViewHolder) {
            UrgentCasesPagerAdapter urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(mContext, urgentCaseList, defaultDrawableResId);
            ((HeaderViewHolder) holder).casesViewPager.setAdapter(urgentCasesPagerAdapter);
        }
    }

//    private void animate(SeekBar seekBar, int progress, int max) {
//        p = progress;
//        ValueAnimator anim = ValueAnimator.ofInt(progress,
//                max);
//        anim.setDuration(100);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(
//                    ValueAnimator animation) {
//                p = (Integer) animation
//                        .getAnimatedValue();
//                seekBar.setProgress(p);
//            }
//        });
//        anim.start();
//    }

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

    public interface CausesAction {
        void onCauseClicked(int productId);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView causeTargetTextView, causeCurrentAmount, causeTitle;
        private ImageView shareImageView;
        private ANImageView causeImage;
        private SeekBar causeSeekBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            causeTargetTextView = (TextView) itemView.findViewById(R.id.cause_target);
            causeCurrentAmount = (TextView) itemView.findViewById(R.id.cause_current_state);
            causeTitle = (TextView) itemView.findViewById(R.id.cause_title);
            causeImage = (ANImageView) itemView.findViewById(R.id.cause_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
            causeSeekBar = (SeekBar) itemView.findViewById(R.id.cause_target_progress_bar);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        ViewPager casesViewPager;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            casesViewPager = (ViewPager) itemView.findViewById(R.id.urgent_cases_view_pager);
        }
    }
}
