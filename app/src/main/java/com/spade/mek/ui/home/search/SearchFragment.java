package com.spade.mek.ui.home.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.volunteering.view.PagingAdapter;

/**
 * Created by Ayman Abouzeid on 9/4/17.
 */

public class SearchFragment extends BaseFragment implements ProductsSearchFragment.CartAction, CausesSearchFragment.CartAction {

    private View view;
    private CartAction cartAction;
    private ProductsSearchFragment productsSearchFragment;
    private CausesSearchFragment causesSearchFragment;
    private NewsSearchFragment newsSearchFragment;
    private PagingAdapter pagingAdapter;
    private ViewPager viewPager;
    private String searchKeyword;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paging, container, false);
        initViews();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initViews() {
        viewPager = (ViewPager) view.findViewById(R.id.fragments_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    private void setupViewPager(ViewPager viewPager) {
        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        pagingAdapter.addFragment(getProductsFragment(), getString(R.string.title_products));
        pagingAdapter.addFragment(getCausesFragment(), getString(R.string.title_causes));
        pagingAdapter.addFragment(getNewsFragment(), getString(R.string.news));
        viewPager.setAdapter(pagingAdapter);
        viewPager.setOffscreenPageLimit(3);
        changeTabsFont();
    }

    private void changeTabsFont() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bahij_semi_bold.ttf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(font);
                    ((TextView) tabViewChild).setTextSize(15);

                }
            }
        }
    }

    private ProductsSearchFragment getProductsFragment() {
        productsSearchFragment = new ProductsSearchFragment();
        productsSearchFragment.setCartAction(this);
        return productsSearchFragment;
    }

    private CausesSearchFragment getCausesFragment() {
        causesSearchFragment = new CausesSearchFragment();
        causesSearchFragment.setCartAction(this);
        return causesSearchFragment;
    }

    private NewsSearchFragment getNewsFragment() {
        newsSearchFragment = new NewsSearchFragment();
        return newsSearchFragment;
    }

    public void search(String keyword) {
//        this.searchKeyword = keyword;
//        ((BaseSearchFragment) pagingAdapter.getItem(viewPager.getCurrentItem())).search(searchKeyword);

        productsSearchFragment.search(keyword);
        causesSearchFragment.search(keyword);
        newsSearchFragment.search(keyword);
    }

    private void searchInCurrentFragment() {
    }

    public void setCartAction(CartAction cartAction) {
        this.cartAction = cartAction;
    }

    @Override
    public void onItemInserted() {
        cartAction.onItemInserted();
    }

    public interface CartAction {
        void onItemInserted();
    }
}
