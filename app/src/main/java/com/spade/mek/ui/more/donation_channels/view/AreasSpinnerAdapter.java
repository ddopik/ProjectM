package com.spade.mek.ui.more.donation_channels.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.donation_channels.model.Area;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/19/17.
 */

public class AreasSpinnerAdapter extends BaseAdapter {
    private List<Area> areaList;
    private Context mContext;

    public AreasSpinnerAdapter(List<Area> areaList, Context mContext) {
        this.areaList = areaList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Area area = areaList.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.type_of_donation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(row);
        viewHolder.cityText.setText(area.getAreaName());
        return row;
    }

    private class ViewHolder {
        TextView cityText;

        ViewHolder(View v) {
            cityText = (TextView) v.findViewById(R.id.item_title);
        }

    }

}
