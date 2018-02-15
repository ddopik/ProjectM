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
import com.spade.mek.ui.more.about.model.AboutUsProjects;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutProjectsAdapter extends RecyclerView.Adapter<AboutProjectsAdapter.AboutUsProjectsAdapterViewHolder> {


    private List<AboutUsProjects> aboutUsList;
    private Context mContext;
    private int defaultDrawableResId;
    private int type;

    public AboutProjectsAdapter(Context context, List<AboutUsProjects> aboutUsProjectItems, int defaultDrawableResId, int type) {
        this.mContext = context;
        this.aboutUsList = aboutUsProjectItems;
        this.defaultDrawableResId = defaultDrawableResId;
        this.type = type;
    }

    @Override
    public AboutUsProjectsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        if (type == LinearLayout.VERTICAL) {
            view = layoutInflater.inflate(R.layout.about_project_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.horizontal_about_project_item, parent, false);
        }
        return new AboutUsProjectsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutUsProjectsAdapterViewHolder holder, int position) {
        AboutUsProjects aboutUsProjectItem = aboutUsList.get(position);
        holder.aboutUsTitle.setText(aboutUsProjectItem.getTitle());

        holder.aboutUsDescription.setText(aboutUsProjectItem.getDescription());
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);

        GlideApp.with(mContext)
                .load(aboutUsProjectItem.getIcon())
                .placeholder(defaultDrawable)
                .centerCrop()
                .into(holder.aboutUsIcon);

        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }


    public class AboutUsProjectsAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView aboutUsIcon;
        TextView aboutUsTitle;
        TextView aboutUsDescription;

        public AboutUsProjectsAdapterViewHolder(View aboutUsViewItem) {
            super(aboutUsViewItem);
            aboutUsIcon = aboutUsViewItem.findViewById(R.id.about_project_icon);
            aboutUsTitle = aboutUsViewItem.findViewById(R.id.about_project_title);
            aboutUsDescription = aboutUsViewItem.findViewById(R.id.about_project_description);

        }
    }
}
