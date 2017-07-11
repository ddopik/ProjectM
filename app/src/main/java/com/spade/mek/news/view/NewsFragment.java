package com.spade.mek.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.news.model.AllNewsResponse;
import com.spade.mek.news.model.News;
import com.spade.mek.news.presenter.NewsPresenter;
import com.spade.mek.news.presenter.NewsPresenterImpl;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsFragment extends BaseFragment implements NewsView, NewsAdapter.OnNewsClicked {

    private NewsPresenter newsPresenter;
    private View newsView;
    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private int currentPage = 0;
    private int lastPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsView = inflater.inflate(R.layout.fragment_news, container, false);
        initViews();
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

        progressBar = (ProgressBar) newsView.findViewById(R.id.progress_bar);
        RecyclerView newsRecyclerView = (RecyclerView) newsView.findViewById(R.id.news_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList, getContext(), ImageUtils.getDefaultImage(appLang));
        newsAdapter.setOnNewsClicked(this);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);

        getNews(appLang);
        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (currentPage < lastPage)) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        getNews(appLang);
                    }
                }
            }
        });
    }

    private void getNews(String appLang) {
        int pageNumber = currentPage + 1;
        isLoading = true;
        newsPresenter.getNews(appLang, pageNumber);
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
        currentPage = allNewsResponse.getNewsData().getCurrentPage();
        lastPage = allNewsResponse.getNewsData().getLastPage();
        if (allNewsResponse.getNewsData().getNewsList() != null) {
            this.newsList.addAll(allNewsResponse.getNewsData().getNewsList());
        }
        newsAdapter.notifyDataSetChanged();
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
}
