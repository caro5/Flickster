package com.example.cwong.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieInfoActivity extends AppCompatActivity {
    private String title;
    private String overview;
    private float popularity;
    private float vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        title = getIntent().getStringExtra("title");
        overview = getIntent().getStringExtra("overview");
        popularity = getIntent().getFloatExtra("popularity", -1);
        vote = getIntent().getFloatExtra("vote", -1);

        TextView titleView = (TextView) findViewById(R.id.tvTitle);
        titleView.setText(title);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbRating);
        ratingBar.setRating(vote);

        TextView popularityView = (TextView) findViewById(R.id.tvPopularity);
        popularityView.setText(String.valueOf(popularity));

        TextView synopsisView = (TextView) findViewById(R.id.tvSynopsis);
        synopsisView.setText(overview);

    }
}
