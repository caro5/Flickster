package com.example.cwong.flickster;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cwong.flickster.databinding.ActivityMovieInfoBinding;
import com.example.cwong.flickster.models.Movie;

public class MovieInfoActivity extends AppCompatActivity {
    private ActivityMovieInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info);

        Intent i = getIntent();
        Movie m = (Movie)i.getSerializableExtra("movie");
        binding.setMovie(m);
    }
}
