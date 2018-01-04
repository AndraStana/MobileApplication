package com.example.dell.moviesapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.moviesapplication.R;
import com.example.dell.moviesapplication.listeners.OnLongClickListenerMovieRecord;
import com.example.dell.moviesapplication.models.Movie;

import java.util.ArrayList;

/**
 * Created by dell on 12/5/2017.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, ArrayList<Movie> movies){
        super(context,0,movies);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie,parent,false);
        }

        TextView movieTitleTextView= (TextView) convertView.findViewById(R.id.movieTitle);
        TextView movieProducerTextView= (TextView) convertView.findViewById(R.id.movieProducer);
        TextView movieIdTextView= (TextView) convertView.findViewById(R.id.movieId);



        movieTitleTextView.setText( movie.getTitle());
        movieProducerTextView.setText("------>    " + movie.getProducer());
        movieIdTextView.setText(movie.getId()+"");

        //movieTitleTextView.setOnLongClickListener(new OnLongClickListenerMovieRecord());
        //movieProducerTextView.setOnLongClickListener(new OnLongClickListenerMovieRecord());
        convertView.setOnLongClickListener(new OnLongClickListenerMovieRecord());


        return convertView;

    }
}
