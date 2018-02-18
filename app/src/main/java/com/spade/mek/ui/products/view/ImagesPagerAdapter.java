package com.spade.mek.ui.products.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.spade.mek.R;
import com.spade.mek.ui.more.news.view.YouTubeNewsActivity;
import com.spade.mek.ui.more.news.view.YouTubeNewsActivity;
import com.spade.mek.utils.GlideApp;
import com.spade.mek.utils.ImageUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ImagesPagerAdapter extends PagerAdapter {


    private Context mContext;
    private int defaultImageResId;
    private List<String> imagesList;
    private OnImageClicked onImageClicked;

    public ImagesPagerAdapter(Context context, List<String> imagesList, int defaultImageResId) {
        mContext = context;
        this.defaultImageResId = defaultImageResId;
        this.imagesList = imagesList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imageUrl = imagesList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.image_item, container,
                false);
        ImageView play_btn = itemView.findViewById(R.id.item_image_play_btn);
        ImageView itemImage = itemView.findViewById(R.id.item_image_view);
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(mContext.getResources(), defaultImageResId, null);

        GlideApp.with(mContext).load(imageUrl)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .into(itemImage);
//        itemImage.setDefaultImageResId(defaultImageResId);
//        itemImage.setErrorImageResId(defaultImageResId);
//        itemImage.setImageUrl(imageUrl);

        //todo A_M [New_task]
        if (position == getCount() - 1) //add youtube listener i case this image is A youtube video
        {
//            VectorDrawableCompat defaultDrawable2 = VectorDrawableCompat.create(mContext.getResources(), defaultImageResId, null);

            if (onImageClicked != null) {
                play_btn.setImageResource(R.drawable.youtube_play_btn);
                itemImage.setOnClickListener(v -> onImageClicked.onImageClicked());
            }

        }


        container.addView(itemView);
        return itemView;
    }

    public void setOnImageClicked(OnImageClicked onImageClicked) {
        this.onImageClicked = onImageClicked;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnImageClicked {
        void onImageClicked();
    }


}
