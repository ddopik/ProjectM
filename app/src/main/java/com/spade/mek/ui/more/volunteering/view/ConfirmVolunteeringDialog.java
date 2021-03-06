package com.spade.mek.ui.more.volunteering.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.regular_products.view.SubscribeFragment;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.FontUtils;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class ConfirmVolunteeringDialog extends DialogFragment {

    private ConfirmActions confirmActions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dilaog_confirmation_regular, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        FontUtils.overrideFonts(getContext(), dialogView);
        return dialogView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void init(View view) {
        TextView confirmMessageFirst = (TextView) view.findViewById(R.id.confirmation_message_first);
        TextView confirmMessageSecond = (TextView) view.findViewById(R.id.confirmation_message_second);

        confirmMessageSecond.setVisibility(View.GONE);
        confirmMessageFirst.setText(getString(R.string.volunteering_confirmed));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        confirmActions.onDismiss();
    }

    public void setConfirmActions(ConfirmActions confirmActions) {
        this.confirmActions = confirmActions;
    }

    public interface ConfirmActions {
        void onDismiss();
    }

}
