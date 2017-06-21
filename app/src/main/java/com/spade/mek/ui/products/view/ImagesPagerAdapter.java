package com.spade.mek.ui.products.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.widget.ANImageView;
import com.spade.mek.R;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ImagesPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int defaultImageResId;
    private List<String> imagesList;

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
        ANImageView itemImage = (ANImageView) itemView.findViewById(R.id.item_image_view);

        itemImage.setDefaultImageResId(defaultImageResId);
        itemImage.setErrorImageResId(defaultImageResId);
        itemImage.setImageUrl(imageUrl);

        container.addView(itemView);
        return itemView;
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
}
