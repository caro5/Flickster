package com.example.cwong.flickster;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cwong.flickster.models.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieVideoActivity extends YouTubeBaseActivity {
    @BindView(R.id.tvError) TextView tvError;
    private String YOUTUBE_API_KEY="AIzaSyBmMNe8gZUEtVBZGt3jPCT3gBCwT7RJu_Q";
    private String trailerUrl;
    private boolean isPopular;
    private ArrayList<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_video);
        ButterKnife.bind(this);

        trailerUrl = getIntent().getStringExtra("trailerUrl");
        isPopular = getIntent().getBooleanExtra("isPopular", false);

        videos = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(trailerUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray videoJsonResults = null;
                try {
                    videoJsonResults = response.getJSONArray("youtube");
                    videos.addAll(Video.fromJSONArray(videoJsonResults));
                    if (videos.size() > 0) {
                        playVideo(videos.get(0).getSource());
                    } else {
                        tvError.setText("No trailers found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
    private void playVideo(final String url) {
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
        playerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (isPopular) {
                    youTubePlayer.loadVideo(url); // plays video
                } else {
                    youTubePlayer.cueVideo(url);  // loads video
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
    }
}
