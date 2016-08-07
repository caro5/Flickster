package com.example.cwong.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by cwong on 8/3/16.
 */
public class Movie implements Serializable {
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTrailerUrl() {
        return String.format("https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", movieId);
    }


    public Float getVote() {
        return vote;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Integer getMovieId() {
        return movieId;
    }

    Integer movieId;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    Float vote;
    Float popularity;


    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.vote = BigDecimal.valueOf(jsonObject.getDouble("vote_average")).floatValue();
        this.popularity = BigDecimal.valueOf(jsonObject.getDouble("popularity")).floatValue();
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public boolean isPopular() {
        return vote > 5.00;
    }
}