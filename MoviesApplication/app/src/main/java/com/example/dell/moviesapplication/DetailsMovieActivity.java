package com.example.dell.moviesapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dell.moviesapplication.enums.Review;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.models.MovieReview;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;
import com.example.dell.moviesapplication.services.RemoteReviewServiceImpl;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsMovieActivity extends AppCompatActivity {


    private static final String TAG = "DetailsActivity";

    private RemoteMovieServiceImpl.RemoteMovieServiceInterface remoteMovieService = RemoteMovieServiceImpl.getInstance();
    private RemoteReviewServiceImpl.RemoteReviewServiceInterface remoteReviewService = RemoteReviewServiceImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("movieTitle");

        readReviews(movieTitle);
        setDetails(movieTitle);
    }

    private void createChart(String movieTitle,Map<String, MovieReview> reviews){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] points = new DataPoint[5];
        int number=0;

        Map<Integer,Integer> dict = createReviewDict(movieTitle,reviews);

        for(Map.Entry<Integer, Integer> entry : dict.entrySet()) {

            int review = entry.getKey();
            int numberOfReviews = entry.getValue();
            points[number] = new DataPoint(review, numberOfReviews);
            number++;
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return  Review.values()[(int)value].toString();
                } else {
                    return super.formatLabel(value, isValueX) ;
                }
            }
        });


    }

    public Map<Integer,Integer> createReviewDict(String movieTitle,Map<String, MovieReview> reviews){
        Map<Integer,Integer> dict= new HashMap<>();
        List<MovieReview> movieReviews = new ArrayList<>();

        for (Map.Entry<String, MovieReview> entry : reviews.entrySet()) {
            if(entry.getValue().getMovieTitle().equals(movieTitle)) {
                movieReviews.add(entry.getValue());
            }
        }

        for(int i = Review.VeryBad.ordinal(); i<=Review.VeryGood.ordinal();i++){
            dict.put(i,0);
        }

        for(MovieReview movieReview : movieReviews){
            dict.put(movieReview.getReview().ordinal(),dict.get(movieReview.getReview().ordinal())+1);
        }
        Map<Integer, Integer> sortedDict = new TreeMap<>(dict);

        return sortedDict;
    }



    public void readReviews(final String title) {
        Call<Map<String, MovieReview>> call = remoteReviewService.getAllReviews();
        call.enqueue(new Callback<Map<String, MovieReview>>() {
            @Override
            public void onResponse(
                    final Call<Map<String, MovieReview>> call,
                    final Response<Map<String, MovieReview>> response) {
                final Map<String, MovieReview> reviews = response.body();
                if (reviews != null && !reviews.isEmpty()) {
                    createChart(title,reviews);
                    Log.d(TAG, "******************onResponse: Reviews found as map with size: " + reviews.size());
                } else {
                    Log.d(TAG, "******************onResponse: No reviews found");
                }
            }

            @Override
            public void onFailure(
                    final Call<Map<String, MovieReview>> call,
                    final Throwable t) {
                Log.e(TAG, "**********************onResume: Failed to find reviews..." + t.getLocalizedMessage(), t);
            }
        });
    }

    public void setDetails(String movieTitle){

        final TextView editTitleMovie = (TextView) findViewById(R.id.textMovieTitle);
        final TextView editProducerMovie = (TextView)findViewById(R.id.textMovieProducer);
        final TextView editYearMovie = (TextView) findViewById(R.id.textMovieYear);
        final TextView editGenreMovie = (TextView) findViewById(R.id.textMovieGenre);
        final TextView editStorylineMovie = (TextView) findViewById(R.id.textMovieStoryline);

        Call<Movie> call = remoteMovieService.getMovie(movieTitle);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(final Call<Movie> call,final Response<Movie> response) {
                    final Movie movie = response.body();
                    if (movie != null ) {
                        editTitleMovie.setText("Title: "+ movie.getTitle());
                        editProducerMovie.setText("Producer: " + movie.getProducer());
                        editYearMovie.setText("Year: " + String.valueOf(movie.getYear()));
                        editGenreMovie.setText("Genre" + movie.getGenre());
                        editStorylineMovie.setText("Storyline: " + movie.getStoryline());
                        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~onResponse: MOVIE: " + movie.getTitle());
                    } else {
                        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~onResponse: No movie found with the TITLE:" );
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e(TAG, "~~~~~~~~~~~~~~~~`onResume: Failed to find movie..." + t.getLocalizedMessage(), t);

                }
            });

    }
}
