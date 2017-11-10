package com.example.dell.moviesapplication.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.moviesapplication.MainActivity;
import com.example.dell.moviesapplication.R;
import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.models.Movie;

/**
 * Created by dell on 10/29/2017.
 */

public class OnClickListenerAddMovie implements View.OnClickListener {

    private MovieController movieController;
    public OnClickListenerAddMovie(MovieController mc){
        movieController = mc;
    }

    public void onClick(View view){
        final Context context = view.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.movie_input_form, null, false);

        final EditText editTextMovieTitle = (EditText) formElementsView.findViewById(R.id.editTextMovieTitle);
        final EditText editTextMovieProducer = (EditText) formElementsView.findViewById(R.id.editTextMovieProducer);
        final EditText editTextMovieYear = (EditText) formElementsView.findViewById(R.id.editTextMovieYear);
        final EditText editTextMovieGenre = (EditText) formElementsView.findViewById(R.id.editTextMovieGenre);
        final EditText editTextMovieStoryline = (EditText) formElementsView.findViewById(R.id.editTextMovieStoryline);

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
                                boolean createSuccessful = new MovieController(context).create(movie);

                                if(createSuccessful){
                                    Toast.makeText(context, "Movie information was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save movie information.", Toast.LENGTH_SHORT).show();
                                }
                               ((MainActivity) context).readRecords();
                                dialog.cancel();
                            }
                        }).show();
    }
}
