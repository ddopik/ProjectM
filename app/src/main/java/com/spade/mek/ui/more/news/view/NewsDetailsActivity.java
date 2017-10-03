package com.spade.mek.ui.more.news.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.news));
        addFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void addFragment() {
        int id = getIntent().getIntExtra(ProductDetailsFragment.ITEM_ID, 1);
        Bundle bundle = new Bundle();
        bundle.putInt(ProductDetailsFragment.ITEM_ID, id);

        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        newsDetailsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newsDetailsFragment).commit();
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, NewsDetailsActivity.class);
    }


}
