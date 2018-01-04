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
import com.example.dell.moviesapplication.MainActivity;
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
 * Created by dell on 11/4/2017.
 */

public class OnLongClickListenerMovieRecord implements View.OnLongClickListener {


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
        final CharSequence[] items = { "Review", "View Details"};

        new AlertDialog.Builder(context).setTitle("Movie Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            reviewMovie(title);
                        } else if (item == 1) {
                            viewDetails(title, context);
                            ((HomeActivity) context).readRecords();

                        }
                        dialog.dismiss();
                    }
                }).show();
        return false;
    }

    public void reviewMovie(final String title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View reviewMovieView = inflater.inflate(R.layout.review_movie, null, false);

        final NumberPicker reviewPicker = (NumberPicker) reviewMovieView.findViewById(R.id.review);
        final String[] values = {"Very bad", "Bad", "Meh", "Good", "Very good"};

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

                                MovieReview review =
                                        new MovieReview(java.util.UUID.randomUUID().toString(),
                                                title,
                                                Review.values()[reviewPicker.getValue()]);
                                addReview(review);
                                ((HomeActivity) context).readRecords();

                                dialog.cancel();
                            }
                        }).show();
    }

         public void viewDetails(final String movieTitle, Context context) {
        Intent myIntent = new Intent(context, DetailsMovieActivity.class);
             myIntent.putExtra("movieTitle", movieTitle);
             context.startActivity(myIntent);
    }



    private void addReview(MovieReview review){
        Call<MovieReview> call = remoteReviewService.createReview(review.getId(), review);
        call.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(
                    final Call<MovieReview> call,
                    final Response<MovieReview> response) {
                Log.d(TAG, "----------------------onResponse:REVIEWWWW  merse");

            }

            @Override
            public void onFailure(
                    final Call<MovieReview> call,
                    final Throwable t) {
                Log.e(TAG, "----------------------onResponse: REVIEWWWW NUUUUU merse....." + t.getLocalizedMessage()  ,t);
            }
        });
    }

}
