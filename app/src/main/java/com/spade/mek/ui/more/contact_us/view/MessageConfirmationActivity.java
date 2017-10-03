package com.spade.mek.ui.more.contact_us.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class MessageConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.confirmation));

        ImageView imageView = (ImageView) findViewById(R.id.logo_image_view);
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parent_layout);
        String appLang = PrefUtils.getAppLang(getApplicationContext());
        imageView.setImageResource(ImageUtils.getOrderConfirmationLogo(appLang));
        overrideFonts(this, parentLayout);
    }

    public void overrideFonts(Context context, View v) {
        if (PrefUtils.getAppLang(context).equals(PrefUtils.ARABIC_LANG)) {
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);

                        overrideFonts(context, child);
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bahij_semi_bold.ttf"));
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getLaunchIntent(Context mContext) {
        return new Intent(mContext, MessageConfirmationActivity.class);
    }
}
