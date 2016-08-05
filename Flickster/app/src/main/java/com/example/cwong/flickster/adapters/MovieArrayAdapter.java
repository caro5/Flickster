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

    public MovieArrayAdapter(Context context, List<Movie>movies) {
        super(context, R.layout.item_movie, movies);
    }

    private int getLayout() {
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return R.layout.item_movie_landscape;
        }
        return R.layout.item_movie;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get layout
        int layout = getLayout();
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

//        // find image view
//        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
//
//        // clear our image from convertView if it was being used
//        ivImage.setImageResource(0);
//
//        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
//        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        // populate data
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());
        if (layout == R.layout.item_movie) {
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.image);
        } else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.backdropImage);
        }
        //return view
        return convertView;
    }
}
