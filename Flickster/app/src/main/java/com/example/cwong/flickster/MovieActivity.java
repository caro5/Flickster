package com.example.cwong.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cwong.flickster.adapters.MovieArrayAdapter;
import com.example.cwong.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    private final int REQUEST_MOVIE_INFO_CODE = 55;

    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.lvMovies) ListView lvItems;

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //code to refresh list
                //remember to call swipeContainer.setRefreshing(false) once network request successly completed
                fetchMoviesAsync(0);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesJsonResults = null;
                try {
                    moviesJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movieAdapter.getItem(position);
                Intent i = new Intent(MovieActivity.this, MovieInfoActivity.class);
                i.putExtra("movie", selectedMovie);
                startActivityForResult(i, REQUEST_MOVIE_INFO_CODE);
            }
        });
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movieAdapter.getItem(position);
                Intent i = new Intent(MovieActivity.this, MovieVideoActivity.class);
                i.putExtra("trailerUrl", selectedMovie.getTrailerUrl());
                i.putExtra("isPopular", selectedMovie.isPopular());
                startActivityForResult(i, REQUEST_MOVIE_INFO_CODE);
                return true;
            }
        });
    }
    public void fetchMoviesAsync(int page) {
        String fetchUrl = "https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        client.get(fetchUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesJsonResults = null;
                try {
                    moviesJsonResults = response.getJSONArray("results");
                    movieAdapter.clear();
                    movieAdapter.addAll(Movie.fromJSONArray(moviesJsonResults));
                    swipeContainer.setRefreshing(false);
                    Log.d("DEBUG", movies.toString());
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
}
