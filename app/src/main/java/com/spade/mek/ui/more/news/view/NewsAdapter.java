package com.spade.mek.ui.more.news.view;

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
import com.spade.mek.ui.more.news.model.News;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context mContext;
    private int defaultDrawableResId;
    private OnNewsClicked onNewsClicked;
    private int type;

    public NewsAdapter(List<News> newsList, Context mContext, int defaultDrawableResId, int type) {
        this.newsList = newsList;
        this.mContext = mContext;
        this.defaultDrawableResId = defaultDrawableResId;
        this.type = type;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        if (type == LinearLayout.VERTICAL) {
            view = layoutInflater.inflate(R.layout.news_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.horizontal_news_item, parent, false);
        }
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newsTitle.setText(news.getTitle());
        holder.newsDescription.setText(news.getShortDescription());
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultDrawableResId, null);
        GlideApp.with(mContext)
                .load(news.getImage())
                .placeholder(defaultDrawable)
                .centerCrop()
                .into(holder.newsImage);
        holder.itemView.setOnClickListener(v -> onNewsClicked.onNewsClicked(news.getId()));
        holder.shareImageView.setOnClickListener(v -> onNewsClicked.onShareClicked(news.getUrl()));
        if (news.getUrl() != null && !news.getUrl().isEmpty()) {
            holder.shareImageView.setVisibility(View.VISIBLE);
        } else {
            holder.shareImageView.setVisibility(View.GONE);
        }
        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    public void setOnNewsClicked(OnNewsClicked onNewsClicked) {
        this.onNewsClicked = onNewsClicked;
    }

    public interface OnNewsClicked {
        void onNewsClicked(int newsId);

        void onShareClicked(String url);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDescription;
        ImageView newsImage, shareImageView;


        public NewsViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            newsDescription = (TextView) itemView.findViewById(R.id.news_description);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            shareImageView = (ImageView) itemView.findViewById(R.id.share_image_view);
        }
    }
}
