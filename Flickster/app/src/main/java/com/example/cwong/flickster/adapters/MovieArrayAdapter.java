package com.example.cwong.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cwong.flickster.R;
import com.example.cwong.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cwong on 8/3/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    // View lookup cache
    private static class ViewHolder {
        ImageView image;
        ImageView backdropImage;
        TextView title;
        TextView overview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.item_movie, movies);
    }

    // Returns # of view types that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isPopular()) {
            return 1;
        }
        ;
        return 0;
    }

    public View getInflatedLayoutForType(int type) {
        if (type == 1) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
    }

    private int getLayout(Integer pos) {
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return R.layout.item_movie_landscape;
        } else {
            if (getItemViewType(pos) == 1) {
                return R.layout.item_movie_popular;
            }
            return R.layout.item_movie;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layout = getLayout(position);
        // get data item for position
        Movie movie = getItem(position);

        ViewHolder viewHolder; //view lookup cache

        // check if existing view is being reused, else inflate view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);

            if (layout == R.layout.item_movie) {
                viewHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            } else {
                viewHolder.backdropImage = (ImageView) convertView.findViewById(R.id.ivBackdropImage);
            }
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (layout == R.layout.item_movie_popular) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.placeholder).into(viewHolder.backdropImage);
        } else {
            viewHolder.title.setText(movie.getOriginalTitle());
            viewHolder.overview.setText(movie.getOverview());
            Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.drawable.placeholder).into(viewHolder.image);
        }
        //return view
        return convertView;
    }
}