package com.spade.mek.ui.home.adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class LatestCausesAdapter extends RecyclerView.Adapter<LatestCausesAdapter.LatestCausesViewHolder> {

    private Context mContext;
    private List<Products> latestCausesList;
    private int defaultDrawableResId;
    private int p;
    private OnCauseClicked onCauseClicked;

    public LatestCausesAdapter(Context context, List<Products> latestCausesList, int defaultDrawableResId) {
        this.mContext = context;
        this.latestCausesList = latestCausesList;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    public LatestCausesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_cause_item, parent, false);
        return new LatestCausesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LatestCausesViewHolder holder, int position) {
        Products latestCause = latestCausesList.get(position);
        holder.causeSeekBar.setMax((int) latestCause.getCauseTarget());
        holder.causeSeekBar.setProgress((int) latestCause.getCauseDone());
        holder.causeSeekBar.setEnabled(false);
        holder.causeTitle.setText(latestCause.getProductTitle());
        holder.causeTargetTextView.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseTarget())));
        holder.causeCurrentAmount.setText(String.format(mContext.getString(R.string.egp), String.valueOf(latestCause.getCauseDone())));

//        holder.causeImage.setDefaultImageResId(defaultDrawableResId);
//        holder.causeImage.setErrorImageResId(defaultDrawableResId);
//        holder.causeImage.setImageUrl(latestCause.getProductImage());

        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(latestCause.getProductImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(holder.causeImage);

        holder.itemView.setOnClickListener(v -> onCauseClicked.onCauseClicked(latestCause.getProductId()));
        holder.shareImageView.setOnClickListener(v -> onCauseClicked.onShareClicked(latestCause.getProductUrl()));
        holder.donateImage.setOnClickListener(v -> onCauseClicked.onDonateLatestCauseClicked(latestCause));

        if (latestCause.getProductUrl() == null || latestCause.getProductUrl().isEmpty()) {
            holder.shareImageView.setVisibility(View.GONE);
        } else {
            holder.shareImageView.setVisibility(View.VISIBLE);
        }
    }

    private void animate(SeekBar seekBar, int progress, int max) {
        p = progress;
        ValueAnimator anim = ValueAnimator.ofInt(progress,
                max);
        anim.setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(
                    ValueAnimator animation) {
                p = (Integer) animation
                        .getAnimatedValue();
                seekBar.setProgress(p);
            }
        });
        anim.start();
    }

    @Override
    public int getItemCount() {
        return latestCausesList.size();
    }

    public void setOnCauseClicked(OnCauseClicked onCauseClicked) {
        this.onCauseClicked = onCauseClicked;
    }

    public interface OnCauseClicked {
        void onCauseClicked(int causeId);

        void onShareClicked(String url);

        void onDonateLatestCauseClicked(Products latestCause);
    }

    public class LatestCausesViewHolder extends RecyclerView.ViewHolder {
        private TextView causeTargetTextView, causeCurrentAmount, causeTitle;
        private ImageView shareImageView;
        private ImageView causeImage;
        private ImageView donateImage;
        private SeekBar causeSeekBar;

        public LatestCausesViewHolder(View itemView) {
            super(itemView);
            causeTargetTextView = (TextView) itemView.findViewById(R.id.cause_target);
            causeCurrentAmount = (TextView) itemView.findViewById(R.id.cause_current_state);
            causeTitle = (TextView) itemView.findViewById(R.id.cause_title);
            causeImage = (ImageView) itemView.findViewById(R.id.cause_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
            donateImage = (ImageView) itemView.findViewById(R.id.donate_image_view);
            causeSeekBar = (SeekBar) itemView.findViewById(R.id.cause_target_progress_bar);
        }
    }
}
