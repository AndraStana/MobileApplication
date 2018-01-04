package com.example.dell.moviesapplication.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.moviesapplication.HomeActivity;
import com.example.dell.moviesapplication.MainActivity;
import com.example.dell.moviesapplication.R;
import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell on 10/29/2017.
 */

public class OnClickListenerAddMovie implements View.OnClickListener {

    private RemoteMovieServiceImpl.RemoteMovieServiceInterface remoteService;
    private static final String TAG = "MyActivity";
    public OnClickListenerAddMovie(RemoteMovieServiceImpl.RemoteMovieServiceInterface service){
        remoteService = service;
    }

    public void onClick(View view){
        final Context context = view.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.add_movie, null, false);

        final EditText editTextMovieTitle = (EditText) formElementsView.findViewById(R.id.addTextMovieTitle);
        final EditText editTextMovieProducer = (EditText) formElementsView.findViewById(R.id.addTextMovieProducer);
        final EditText editTextMovieYear = (EditText) formElementsView.findViewById(R.id.addTextMovieYear);
        final EditText editTextMovieGenre = (EditText) formElementsView.findViewById(R.id.addTextMovieGenre);
        final EditText editTextMovieStoryline = (EditText) formElementsView.findViewById(R.id.addTextMovieStoryline);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Add Movie")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String title = editTextMovieTitle.getText().toString();
                                String producer = editTextMovieProducer.getText().toString();
                                int year =Integer.parseInt(editTextMovieYear.getText().toString());
                                String genre = editTextMovieGenre.getText().toString();
                                String storyline = editTextMovieStoryline.getText().toString();

                                Movie movie = new Movie(title,producer,year,genre,storyline);
                                addMovie(movie);

                               //((MainActivity) context).readRecords();
                                ((HomeActivity) context).readRecords();

                                dialog.cancel();
                            }
                        }).show();
    }



    private void addMovie(Movie movie){
        Call<Movie> call = remoteService.createMovie(movie.getTitle(), movie);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(
                    final Call<Movie> call,
                    final Response<Movie> response) {
                Log.d(TAG, "----------------------onResponse: merse");
            }

            @Override
            public void onFailure(
                    final Call<Movie> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse:NUUUUU merse....." + t.getLocalizedMessage()  ,t);
            }
        });
    }

}
