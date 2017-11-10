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
 * Created by dell on 11/4/2017.
 */

public class OnLongClickListenerMovieRecord implements View.OnLongClickListener {

        Context context;
        String id;

        @Override
        public boolean onLongClick(View view) {


            context = view.getContext();
            id = view.getTag().toString();
            final CharSequence[] items = { "Edit", "Delete" };

            new AlertDialog.Builder(context).setTitle("Movie Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                editMovie(Integer.parseInt(id));
                            }

                            else if (item == 1) {
                                boolean deleteSuccessful = new MovieController(context).delete(Integer.parseInt(id));
                                if (deleteSuccessful){
                                    Toast.makeText(context, "Movie record was deleted.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to delete movie record.", Toast.LENGTH_SHORT).show();
                                }
                                ((MainActivity) context).readRecords();
                            }
                            dialog.dismiss();
                        }
                    }).show();
            return false;
    }
    public void editMovie(final int movieId) {
        final MovieController ctrl = new MovieController(context);
        Movie movie = ctrl.readSingleRecord(movieId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.movie_input_form, null, false);

        final EditText editTitleMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieTitle);
        final EditText editProducerMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieProducer);
        final EditText editYearMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieYear);
        final EditText editGenreMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieGenre);
        final EditText editStorylineMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieStoryline);

        editTitleMovie.setText(movie.getTitle());
        editProducerMovie.setText(movie.getProducer());
        editYearMovie.setText(String.valueOf(movie.getYear()));
        editGenreMovie.setText(movie.getGenre());
        editStorylineMovie.setText(movie.getStoryline());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Movie movie = new Movie();
                                movie.setId(movieId);
                                movie.setTitle(editTitleMovie.getText().toString());
                                movie.setProducer(editProducerMovie.getText().toString());
                                movie.setYear(Integer.parseInt(editYearMovie.getText().toString()));
                                movie.setGenre(editGenreMovie.getText().toString());
                                movie.setStoryline(editStorylineMovie.getText().toString());

                                boolean updateSuccessful = ctrl.update(movie);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Movie record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update movie record.", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity) context).readRecords();

                                dialog.cancel();
                            }

                        }).show();
    }
}
