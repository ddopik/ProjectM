package com.spade.mek.ui.more.donation_channels.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.donation_channels.model.City;
import com.spade.mek.utils.FontUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/19/17.
 */

public class CitiesSpinnerAdapter extends BaseAdapter {
    private List<City> cityList;
    private Context mContext;

    public CitiesSpinnerAdapter(List<City> cityList, Context mContext) {
        this.cityList = cityList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = cityList.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.type_of_donation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        viewHolder.cityText.setText(city.getCityName());
        FontUtils.overrideFonts(mContext, row);
        return row;
    }

    private class ViewHolder {
        TextView cityText;

        ViewHolder(View v) {
            cityText = (TextView) v.findViewById(R.id.item_title);
        }

    }
}
