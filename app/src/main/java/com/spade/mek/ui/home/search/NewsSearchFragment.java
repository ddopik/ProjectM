package com.spade.mek.ui.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseSearchFragment;
import com.spade.mek.ui.home.search.model.NewsSearchResponse;
import com.spade.mek.ui.more.news.model.AllNewsResponse;
import com.spade.mek.ui.more.news.model.News;
import com.spade.mek.ui.more.news.presenter.NewsPresenter;
import com.spade.mek.ui.more.news.presenter.NewsPresenterImpl;
import com.spade.mek.ui.more.news.view.NewsAdapter;
import com.spade.mek.ui.more.news.view.NewsDetailsActivity;
import com.spade.mek.ui.more.news.view.NewsView;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsSearchFragment extends BaseSearchFragment implements NewsView, NewsAdapter.OnNewsClicked {

    private NewsPresenter newsPresenter;
    private View newsView;
    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;
    private LinearLayout lookingForLayout, noSearchResult;
    private RecyclerView newsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsView = inflater.inflate(R.layout.fragment_news, container, false);
        initViews();
        overrideFonts(getContext(), newsView);

        Tracker tracker = MekApplication.getDefaultTracker();
        tracker.setScreenName(getResources().getString(R.string.news_search_screen));
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        return newsView;
    }

    @Override
    protected void initPresenter() {
        newsPresenter = new NewsPresenterImpl(getContext());
        newsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        lookingForLayout = (LinearLayout) newsView.findViewById(R.id.looking_for_layout);
        noSearchResult = (LinearLayout) newsView.findViewById(R.id.nothing_matches_layout);
        newsRecyclerView = (RecyclerView) newsView.findViewById(R.id.news_recycler_view);
        progressBar = (ProgressBar) newsView.findViewById(R.id.progress_bar);
        lookingForLayout.setVisibility(View.VISIBLE);
        newsRecyclerView.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList, getContext(), ImageUtils.getDefaultImage(appLang), LinearLayout.VERTICAL);
        newsAdapter.setOnNewsClicked(this);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
    }


    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNews(AllNewsResponse allNewsResponse) {
    }

    @Override
    public void showSearchResults(NewsSearchResponse newsSearchResponse) {
        this.newsList.clear();
        if (newsSearchResponse.getNewsList() != null) {
            this.newsList.addAll(newsSearchResponse.getNewsList());
        }
        newsAdapter.notifyDataSetChanged();

        lookingForLayout.setVisibility(View.GONE);
        if (this.newsList.isEmpty()) {
            noSearchResult.setVisibility(View.VISIBLE);
        } else {
            noSearchResult.setVisibility(View.GONE);
            newsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNewsClicked(int newsId) {
        Intent intent = NewsDetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void onShareClicked(String url) {
        newsPresenter.shareItem(url);
    }

    @Override
    public void search(String searchKeyword) {
        newsPresenter.search(searchKeyword);
    }
}
