package com.spade.mek.ui.more.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.news.model.News;
import com.spade.mek.ui.more.news.model.NewsCategory;
import com.spade.mek.ui.more.news.presenter.NewsDetailsPresenter;
import com.spade.mek.ui.more.news.presenter.NewsDetailsPresenterImpl;
import com.spade.mek.ui.products.view.ImagesPagerAdapter;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.spade.mek.ui.more.news.view.YouTubeNewsActivity.API_KEY;


/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class NewsDetailsFragment extends BaseFragment implements NewsDetailsView, NewsAdapter.OnNewsClicked, ImagesPagerAdapter.OnImageClicked {
    public static final String ITEM_ID = "ITEM_ID";
    public static final String YOUTUBE_CODE = "VEDIO_YOUTYBE_ID";

    private View newsDetailsView;
    private TextView newsTitle, newsCategory, newsDetails,
            newsCreatedAt, relatedNewsTextView;
    private ImageView shareImage;
    private ProgressBar progressBar;

    private NewsDetailsPresenter newsDetailsPresenter;
    private ImagesPagerAdapter imagesPagerAdapter;
    private NewsAdapter newsAdapter;
    private RecyclerView relatedNewsRecycler;
    private List<String> imagesList;
    private List<News> newsList;

    private int itemId;
    private String itemUrl = "";
    //todo A_M [New_task]
    private String itemYouTubeUrl = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getArguments().getInt(ITEM_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsDetailsView = inflater.inflate(R.layout.fragment_news_details, container, false);
        initViews();
        overrideFonts(getContext(), newsDetailsView);

        return newsDetailsView;
    }

    @Override
    protected void initPresenter() {
        newsDetailsPresenter = new NewsDetailsPresenterImpl(getContext());
        newsDetailsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        imagesList = new ArrayList<>();
        newsList = new ArrayList<>();

        relatedNewsRecycler = newsDetailsView.findViewById(R.id.related_news_recycler);
        ViewPager imagesViewPager = newsDetailsView.findViewById(R.id.product_images_view_pager);
        String appLang = PrefUtils.getAppLang(getContext());

        newsTitle = newsDetailsView.findViewById(R.id.item_title);
        newsCategory = newsDetailsView.findViewById(R.id.item_category);
        newsDetails = newsDetailsView.findViewById(R.id.item_details);
        newsCreatedAt = newsDetailsView.findViewById(R.id.item_publish_date);
        relatedNewsTextView = newsDetailsView.findViewById(R.id.related_news);
        shareImage = newsDetailsView.findViewById(R.id.share_image_view);
        progressBar = newsDetailsView.findViewById(R.id.progress_bar);

        imagesPagerAdapter = new ImagesPagerAdapter(getContext(), imagesList, ImageUtils.getDefaultImage(appLang));
        imagesPagerAdapter.setOnImageClicked(this);
        imagesViewPager.setAdapter(imagesPagerAdapter);

        newsAdapter = new NewsAdapter(newsList, getContext(), ImageUtils.getDefaultImage(appLang), LinearLayout.HORIZONTAL);
        newsAdapter.setOnNewsClicked(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, newsDetailsPresenter.isReverse(appLang));
        relatedNewsRecycler.setLayoutManager(linearLayoutManager);
        relatedNewsRecycler.setAdapter(newsAdapter);

        shareImage.setOnClickListener(v -> newsDetailsPresenter.shareItem(itemUrl));
        newsDetailsPresenter.getNewsDetails(appLang, itemId);
        getRelatedNews(appLang);


    }

    private void getRelatedNews(String appLang) {
        newsDetailsPresenter.getRelatedNews(appLang, itemId);
    }

    @Override
    public void onError(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {

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
    public void updateUI(News news) {
        if (news.getCreatedAt() == 0) {
            newsCreatedAt.setVisibility(View.GONE);
        } else {
            newsCreatedAt.setVisibility(View.VISIBLE);
            newsCreatedAt.setText(String.format(getString(R.string.published_at), getDate(news.getCreatedAt())));
        }

        List<NewsCategory> newsCategoryList = news.getCategories();
        if (newsCategoryList != null && !newsCategoryList.isEmpty()) {
            String category = "";
            for (int i = 0; i < newsCategoryList.size(); i++) {
                if (i == newsCategoryList.size() - 1) {
                    category += newsCategoryList.get(i).getCategoryName();
                } else {
                    category += newsCategoryList.get(i).getCategoryName() + " / ";
                }
            }
            newsCategory.setText(category);
        } else {
            newsCategory.setVisibility(View.GONE);
        }

        itemUrl = news.getUrl();
        if (itemUrl == null || itemUrl.isEmpty()) {
            shareImage.setVisibility(View.INVISIBLE);
        } else {
            shareImage.setVisibility(View.VISIBLE);
        }

        newsTitle.setText(news.getTitle());
        newsDetails.setText(news.getBody());
        imagesList.add(news.getImage());
//        itemYouTubeUrl = (!news.getYoutubeUrl().isEmpty()) ? itemYouTubeUrl = news.getYoutubeUrl() : "";
        addYoutubeImageToAdapter(news);
        imagesPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRelatedNews(List<News> relatedNews) {
        newsList.addAll(relatedNews);
        if (!relatedNews.isEmpty()) {
            relatedNewsTextView.setVisibility(View.VISIBLE);
            relatedNewsRecycler.setVisibility(View.VISIBLE);
            newsAdapter.notifyDataSetChanged();
        }
    }

    private String getDate(long timeStamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        DateFormat dateFormatter = DateFormat.getDateInstance();
        dateFormatter.setTimeZone(timeZone);

        Calendar calendar =
                Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(timeStamp * 1000);
        String result = dateFormatter.format(calendar.getTime());
        calendar.clear();
        return result;
    }

    @Override
    public void onNewsClicked(int newsId) {
        Intent intent = NewsDetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(ProductDetailsFragment.ITEM_ID, newsId);
        startActivity(intent);
        getActivity().finish();
    }

    //todo A_M [New_task]
    @Override // youTube of SingleNews
    public void onImageClicked() {
        if (itemYouTubeUrl.equals(""))
            return;
//        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), API_KEY, itemYouTubeUrl);
//        intent.putExtra(YOUTUBE_CODE, itemYouTubeUrl);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), YouTubeNewsActivity.class);
        intent.putExtra(YOUTUBE_CODE, itemYouTubeUrl);
        startActivity(intent);
    }

    //todo A_M [New_task]
    ///append youTube Image to ImageAdapter { ---[Tale] }
    public void addYoutubeImageToAdapter(News news) {
        if (!news.getYoutubeUrl().isEmpty()) {
            itemYouTubeUrl = news.getYoutubeUrl();
            //todo A_M [New_task][2]
            imagesList.add(news.getImage());
        }
        itemYouTubeUrl = (!news.getYoutubeUrl().isEmpty()) ? itemYouTubeUrl = news.getYoutubeUrl() : "";
    }

    @Override
    public void onShareClicked(String url) {
        newsDetailsPresenter.shareItem(url);
    }
}
