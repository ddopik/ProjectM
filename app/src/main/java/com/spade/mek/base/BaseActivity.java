package com.spade.mek.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.view.CartActivity;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private TextView numberTextView;
    private View badgeView;
    private RealmDbHelper realmDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);
        badgeView = menu.findItem(R.id.cart_item).getActionView();
        numberTextView = (TextView) badgeView.findViewById(R.id.items_count);
        badgeView.setOnClickListener(v -> startActivity(CartActivity.getLaunchIntent(this)));
        updateCounter();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.cart_item:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateCounter() {
        long itemsCount = realmDbHelper.getItemsCount(PrefUtils.getUserId(this));
        if (numberTextView != null) {
            badgeView.setVisibility(View.VISIBLE);
            if (itemsCount > 0) {
                numberTextView.setText(String.valueOf(itemsCount));
                numberTextView.setVisibility(View.VISIBLE);
            } else {
                numberTextView.setVisibility(View.GONE);
            }
        }
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


    protected abstract void addFragment();

    protected abstract void addFragment(String title, Fragment fragment);
}
