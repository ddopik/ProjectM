package com.spade.mek.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.NavigationManager;

/**
 * Created by Ayman Abouzeid on 6/21/17.
 */

public class DetailsActivity extends BaseActivity {

    public static final String SCREEN_TITLE = "SCREEN_TITLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setTitle(getIntent().getStringExtra(SCREEN_TITLE));
        addFragment();
    }

    @Override
    protected void addFragment() {
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ProductDetailsFragment.ITEM_ID, getIntent().getIntExtra(ProductDetailsFragment.ITEM_ID, 1));
        productDetailsFragment.setArguments(bundle);
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, productDetailsFragment, this, DetailsActivity.class.getSimpleName());
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, DetailsActivity.class);
    }
}
