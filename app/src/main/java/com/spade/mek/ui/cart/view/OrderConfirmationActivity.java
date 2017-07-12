package com.spade.mek.ui.cart.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_created_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.confirmation));

        ImageView imageView = (ImageView) findViewById(R.id.logo_image_view);
        TextView confirmationTextView = (TextView) findViewById(R.id.confirmation_message);
        confirmationTextView.setText(String.format(getString(R.string.donation_success), getIntent().getStringExtra(UserDataFragment.EXTRA_TOTAL_COST)));

        String appLang = PrefUtils.getAppLang(getApplicationContext());
        imageView.setImageResource(ImageUtils.getOrderConfirmationLogo(appLang));
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
        return new Intent(mContext, OrderConfirmationActivity.class);
    }
}