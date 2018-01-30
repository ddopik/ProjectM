package com.spade.mek.ui.more.news.view;

import android.content.res.Configuration;
import android.os.Bundle;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.spade.mek.R;
import static com.spade.mek.ui.more.news.view.NewsDetailsFragment.YOUTUBE_CODE;

/**
 * Created by ddopik on 1/24/2018.
 */

public class YouTubeNewsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    //todo-->[New_task] new youTubeActivity
    public static final String API_KEY = "AIzaSyB3uJ-dzR0RBzDfq6bxBwRYyJihjBxYjAw";
    //http://youtu.be/<VIDEO_ID>
    private String VIDEO_ID = "TxNRQfUnDJA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** attaching layout xml **/
        setContentView(R.layout.you_tube_news_activity);

        /** Initializing YouTube player view **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        VIDEO_ID = getIntent().getExtras().getString(YOUTUBE_CODE);
        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            player.setFullscreen(true);
            player.play();
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}