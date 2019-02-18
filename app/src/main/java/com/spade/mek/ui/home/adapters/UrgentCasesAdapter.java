package com.spade.mek.ui.home.adapters;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.home.urgent_cases.UrgentCase;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class UrgentCasesAdapter extends RecyclerView.Adapter<UrgentCasesAdapter.LatestCausesViewHolder> {

    private Context mContext;
    private List<UrgentCase> urgentCaseList;
    private int defaultDrawableResId;

    public UrgentCasesAdapter(Context context, List<UrgentCase> urgentCaseList, int defaultDrawableResId) {
        this.mContext = context;
        this.urgentCaseList = urgentCaseList;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    public LatestCausesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.urgent_case_item, parent, false);
        return new LatestCausesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LatestCausesViewHolder holder, int position) {
        UrgentCase urgentCase = urgentCaseList.get(position);
        holder.caseTitle.setText(urgentCase.getCaseTitle());
//        holder.caseImage.setDefaultImageResId(defaultDrawableResId);
//        holder.caseImage.setErrorImageResId(defaultDrawableResId);
//        holder.caseImage.setImageUrl(urgentCase.getCaseImage());
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext).load(urgentCase.getCaseImage()).centerCrop().
                placeholder(defaultDrawable).error(defaultDrawable).into(holder.caseImage);
    }

    @Override
    public int getItemCount() {
        return urgentCaseList.size();
    }

    public class LatestCausesViewHolder extends RecyclerView.ViewHolder {
        private TextView caseTitle;
        private ImageView shareImageView;
        private ImageView caseImage;


        public LatestCausesViewHolder(View itemView) {
            super(itemView);
            caseTitle = (TextView) itemView.findViewById(R.id.case_title);
            caseImage = (ImageView) itemView.findViewById(R.id.case_image_view);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);

        }
    }
}
