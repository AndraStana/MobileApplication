package com.example.dell.moviesapplication.listeners;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.moviesapplication.DetailsMovieActivity;
import com.example.dell.moviesapplication.MainActivity;
import com.example.dell.moviesapplication.R;
import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.controller.MovieReviewController;
import com.example.dell.moviesapplication.enums.Review;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.models.MovieReview;

/**
 * Created by dell on 11/4/2017.
 */

public class OnLongClickListenerMovieRecord implements View.OnLongClickListener {

        Context context;
        String id;

        @Override
        public boolean onLongClick(View view) {


            context = view.getContext();
            //id = view.getTag().toString();
            TextView movieIdTextView = (TextView) view.findViewById(R.id.movieId);
            id=movieIdTextView.getText().toString();
            final CharSequence[] items = { "Edit", "Delete","Review","View Details" };

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

                            else if(item==2){
                                reviewMovie(Integer.parseInt(id));
                            }
                            else if(item==3){
                                viewDetails(Integer.parseInt(id), context);
                            }


                            dialog.dismiss();
                        }
                    }).show();
            return false;
    }


    public void reviewMovie(final int movieId){
        final MovieReviewController movieReviewCtrl = new MovieReviewController(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View reviewMovieView = inflater.inflate(R.layout.review_movie, null, false);

        final NumberPicker reviewPicker = (NumberPicker) reviewMovieView.findViewById(R.id.review);
        final String[] values= {"Very bad","Bad", "Meh", "Good", "Very good"};

        reviewPicker.setMinValue(Review.VeryBad.ordinal());
        reviewPicker.setMaxValue(Review.VeryGood.ordinal());
        reviewPicker.setWrapSelectorWheel(true);
        reviewPicker.setDisplayedValues(values);
        new AlertDialog.Builder(context)
                .setView(reviewMovieView)
                .setTitle("Review")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                MovieReview movieReview = new MovieReview();
                                movieReview.setMovieId(movieId);
                                movieReview.setReview(Review.values()[reviewPicker.getValue()]);

                                boolean createSuccessful = movieReviewCtrl.create(movieReview);

                                if(createSuccessful){
                                    Toast.makeText(context, "Movie was reviewed.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to review movie.", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity) context).readRecords();

                                dialog.cancel();
                            }

                        }).show();

    }

    public void viewDetails(final int movieId, Context context){

        final MovieController ctrl = new MovieController(context);
        Movie movie = ctrl.readSingleRecord(movieId);

        Intent myIntent = new Intent(context, DetailsMovieActivity.class);
        myIntent.putExtra("movieId", movieId); //Optional parameters
        context.startActivity(myIntent);
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
