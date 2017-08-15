package com.spade.mek.ui.more.donation_channels.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.donation_channels.model.Store;
import com.spade.mek.utils.FontUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoreViewHolder> {

    private List<Store> storeList;
    private Context mContext;
    private StoreActions storeActions;

    public StoresAdapter(List<Store> storeList, Context mContext) {
        this.storeList = storeList;
        this.mContext = mContext;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.store_item, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        Store store = storeList.get(position);

        if (store.getTitle() != null && !store.getTitle().isEmpty()) {
            holder.storeTitle.setText(store.getTitle());
        }

        if (store.getAddress() != null && !store.getAddress().isEmpty()) {
            holder.storeAddress.setText(store.getAddress());
        } else {
            holder.addressLayout.setVisibility(View.GONE);
        }

        if (store.getContractsFax() != null) {
            holder.sectorFax.setText(String.valueOf(store.getContractsFax()));
        } else {
            holder.sectorFaxLayout.setVisibility(View.GONE);
        }

        if (store.getWorkingHours() != null && !store.getWorkingHours().isEmpty()) {
            holder.workingHours.setText(store.getWorkingHours());
        } else {
            holder.workingHoursLayout.setVisibility(View.GONE);
        }


        if (store.getTelephone() != null && !store.getTelephone().isEmpty()) {
            String storePhones = "";
            for (int i = 0; i < store.getTelephone().size(); i++) {
                if (i % 2 == 0 && i != store.getTelephone().size() - 1) {
                    storePhones += store.getTelephone().get(i) + "-";
                } else if (i % 2 == 0 && i == store.getTelephone().size() - 1) {
                    storePhones += store.getTelephone().get(i);
                }

                if (i % 2 != 0 && i != store.getTelephone().size() - 1) {
                    storePhones += store.getTelephone().get(i) + "\n";
                } else if (i % 2 != 0 && i == store.getTelephone().size() - 1) {
                    storePhones += store.getTelephone().get(i);
                }
            }
            holder.storePhone.setText(storePhones);
        } else {
            holder.phoneLayout.setVisibility(View.GONE);
        }

        if (store.getFax() != null && !store.getFax().isEmpty()) {
            String storePhones = "";
            for (int i = 0; i < store.getFax().size(); i++) {
                if (i % 2 == 0 && i != store.getFax().size() - 1) {
                    storePhones += store.getFax().get(i) + "-";
                } else if (i % 2 == 0 && i == store.getFax().size() - 1) {
                    storePhones += store.getFax().get(i);
                }

                if (i % 2 != 0 && i != store.getFax().size() - 1) {
                    storePhones += store.getFax().get(i) + "\n";
                } else if (i % 2 != 0 && i == store.getFax().size() - 1) {
                    storePhones += store.getFax().get(i);
                }
            }
            holder.storeFax.setText(storePhones);
        } else {
            holder.faxLayout.setVisibility(View.GONE);
        }
        holder.checkDirection.setOnClickListener(v -> storeActions.onCheckDirectionClicked(
                store.getLat(),
                store.getLng()));

        FontUtils.overrideFonts(mContext, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void setStoreActions(StoreActions storeActions) {
        this.storeActions = storeActions;
    }

    public interface StoreActions {
        void onCheckDirectionClicked(String lat, String lng);
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView storeTitle, storeAddress, storePhone, storeFax, sectorFax, workingHours, checkDirection;
        LinearLayout addressLayout, phoneLayout, faxLayout, sectorFaxLayout, workingHoursLayout;

        public StoreViewHolder(View itemView) {
            super(itemView);
            storeTitle = (TextView) itemView.findViewById(R.id.header_title);
            storeAddress = (TextView) itemView.findViewById(R.id.address_text);
            storePhone = (TextView) itemView.findViewById(R.id.phone_text);
            storeFax = (TextView) itemView.findViewById(R.id.fax_text);
            sectorFax = (TextView) itemView.findViewById(R.id.contract_fax_text);
            workingHours = (TextView) itemView.findViewById(R.id.working_hours_text);
            checkDirection = (TextView) itemView.findViewById(R.id.check_direction);
            addressLayout = (LinearLayout) itemView.findViewById(R.id.address_layout);
            phoneLayout = (LinearLayout) itemView.findViewById(R.id.phone_layout);
            faxLayout = (LinearLayout) itemView.findViewById(R.id.fax_layout);
            sectorFaxLayout = (LinearLayout) itemView.findViewById(R.id.contract_fax_layout);
            workingHoursLayout = (LinearLayout) itemView.findViewById(R.id.working_hours_layout);
        }
    }
}
