package com.spade.mek.ui.more.about.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.about.model.AboutUsItem;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder> {


    private List<AboutUsItem> aboutUsList;
    private Context mContext;
    private int defaultDrawableResId;
    private int type;

    public void AboutUsAdapter(Context context, List<AboutUsItem> aboutUsList, int defaultDrawableResId, int type) {
        this.mContext = context;
        this.aboutUsList = aboutUsList;
        this.defaultDrawableResId = defaultDrawableResId;
        this.type = type;
    }

    @Override
    public AboutUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        if (type == LinearLayout.VERTICAL) {
            view = layoutInflater.inflate(R.layout.about_us_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.horizontal_about_us_item, parent, false);
        }
        return new AboutUsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutUsViewHolder holder, int position) {
        AboutUsItem aboutUsItem = aboutUsList.get(position);
        holder.aboutUsTitle.setText(aboutUsItem.getAboutTitle());

        holder.aboutUsDescription.setText(aboutUsItem.getAboutDescriptionl());
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);

        GlideApp.with(mContext)
                .load(aboutUsItem.getAboutIcon())
                .placeholder(defaultDrawable)
                .centerCrop()
                .into(holder.aboutUsIcon);

        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }


    public class AboutUsViewHolder extends RecyclerView.ViewHolder {

        ImageView aboutUsIcon;
        TextView aboutUsTitle;
        TextView aboutUsDescription;

        public AboutUsViewHolder(View aboutUsViewItem) {
            super(aboutUsViewItem);
//             aboutUsIcon=(ImageView) aboutUsViewItem.findViewById(R.id.about_us_icon);
//             aboutUsTitle=(TextView) aboutUsViewItem.findViewById(R.id.about_us_title);
//             aboutUsDescription =(TextView) aboutUsViewItem.findViewById(R.id.about_us_description);

        }
    }
}
