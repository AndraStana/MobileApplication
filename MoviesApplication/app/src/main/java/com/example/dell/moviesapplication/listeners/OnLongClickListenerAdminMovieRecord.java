package com.example.dell.moviesapplication.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.dell.moviesapplication.DetailsMovieActivity;
import com.example.dell.moviesapplication.HomeActivity;
import com.example.dell.moviesapplication.HomeAdminActivity;
import com.example.dell.moviesapplication.R;
import com.example.dell.moviesapplication.enums.Review;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.models.MovieReview;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;
import com.example.dell.moviesapplication.services.RemoteReviewServiceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell on 1/3/2018.
 */

public class OnLongClickListenerAdminMovieRecord implements View.OnLongClickListener {

    private RemoteMovieServiceImpl.RemoteMovieServiceInterface remoteMovieService = RemoteMovieServiceImpl.getInstance();
    private RemoteReviewServiceImpl.RemoteReviewServiceInterface remoteReviewService = RemoteReviewServiceImpl.getInstance();
    private static final String TAG = "MyActivity";
    Context context;
    String title;

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        TextView movieTitleTextView = (TextView) view.findViewById(R.id.movieTitle);

        title = movieTitleTextView.getText().toString().trim();
        final CharSequence[] items = {"Edit", "Delete","View Details"};

        new AlertDialog.Builder(context).setTitle("Movie Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            getMovie(title);
                        } else if (item == 1) {
                            deleteMovie(title);
                            ((HomeAdminActivity) context).readRecords();

                        }
                         else if (item == 2) {
                            viewDetails(title, context);
                        }
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }


    public void viewDetails(final String movieTitle, Context context) {
        Intent myIntent = new Intent(context, DetailsMovieActivity.class);
        myIntent.putExtra("movieTitle", movieTitle);
        context.startActivity(myIntent);
    }

    public void getMovie(final String title) {
        Call<Movie> call = remoteMovieService.getMovie(title);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(final Call<Movie> call,final Response<Movie> response) {
                final Movie movie = response.body();
                if (movie != null ) {
                    editMovie(movie);
                    Log.d(TAG, "~~~~~~~~~~~~~~~~~~~onResponse: MOVIE: " + movie.getProducer());
                } else {
                    Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~onResponse: No movie found with the TITLE:" + title);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "~~~~~~~~~~~~~~~~`onResume: Failed to find movie..." + t.getLocalizedMessage(), t);

            }
        });
    }



    public void editMovie(Movie movie) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.movie_input_form, null, false);

        final TextView textTitleMovie = (TextView) formElementsView.findViewById(R.id.editTextMovieTitle);
        final EditText editProducerMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieProducer);
        final EditText editYearMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieYear);
        final EditText editGenreMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieGenre);
        final EditText editStorylineMovie = (EditText) formElementsView.findViewById(R.id.editTextMovieStoryline);

        textTitleMovie.setText(movie.getTitle());
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
                                movie.setTitle(textTitleMovie.getText().toString());
                                movie.setProducer(editProducerMovie.getText().toString());
                                movie.setYear(Integer.parseInt(editYearMovie.getText().toString()));
                                movie.setGenre(editGenreMovie.getText().toString());
                                movie.setStoryline(editStorylineMovie.getText().toString());

                                updateMovie(movie);

                                ((HomeAdminActivity) context).readRecords();


                                dialog.cancel();
                            }
                        }).show();
    }


    private void updateMovie(Movie movie) {
        Call<Movie> call = remoteMovieService.createMovie(movie.getTitle(), movie);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(
                    final Call<Movie> call,
                    final Response<Movie> response) {
                //((MainActivity) context).readRecords();
                ((HomeAdminActivity) context).readRecords();

                Log.d(TAG, "----------------------onResponse: merse");
            }

            @Override
            public void onFailure(
                    final Call<Movie> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse:NUUUUU merse....." + t.getLocalizedMessage(), t);
            }
        });
    }


    private void deleteMovie(String title){
        Call<Movie> call = remoteMovieService.deleteMovie(title);
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
                Log.e(TAG, "----------------------onResponse:NUUUUU merse....." + t.getLocalizedMessage(), t);
            }
        });
    }
}
