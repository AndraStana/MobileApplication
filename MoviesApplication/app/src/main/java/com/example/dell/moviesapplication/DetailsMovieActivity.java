package com.example.dell.moviesapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.controller.MovieReviewController;
import com.example.dell.moviesapplication.enums.Review;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.models.MovieReview;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DetailsMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId",0);

        createChart(movieId);
        setDetails(movieId);
    }

    private void createChart(int movieId){
         GraphView graph = (GraphView) findViewById(R.id.graph);
         DataPoint[] points = new DataPoint[5];
         int number=0;

        Map<Integer,Integer> dict = createReviewDict(movieId);

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
                    return  Review.values()[(int)value].toString();//super.formatLabel(value, isValueX);
                } else {
                    return super.formatLabel(value, isValueX) ;
                }
            }
        });


    }

    public Map<Integer,Integer> createReviewDict(int movieId){

        Map<Integer,Integer> dict= new HashMap<>();
        MovieReviewController ctrl = new MovieReviewController(this);
        List<MovieReview> movieReviews = ctrl.getReviewsOfMovie(movieId);

        for(int i = Review.VeryBad.ordinal(); i<=Review.VeryGood.ordinal();i++){
            dict.put(i,0);
        }

        for(MovieReview movieReview : movieReviews){
            dict.put(movieReview.getReview().ordinal(),dict.get(movieReview.getReview().ordinal())+1);
        }
        Map<Integer, Integer> sortedDict = new TreeMap<>(dict);
        return sortedDict;
    }


    public void setDetails(int movieId){


        final MovieController ctrl = new MovieController(this);
        Movie movie = ctrl.readSingleRecord(movieId);

        final TextView editTitleMovie = (TextView) findViewById(R.id.textMovieTitle);
        final TextView editProducerMovie = (TextView)findViewById(R.id.textMovieProducer);
        final TextView editYearMovie = (TextView) findViewById(R.id.textMovieYear);
        final TextView editGenreMovie = (TextView) findViewById(R.id.textMovieGenre);
        final TextView editStorylineMovie = (TextView) findViewById(R.id.textMovieStoryline);

        editTitleMovie.setText("Title: "+ movie.getTitle());
        editProducerMovie.setText("Producer: " + movie.getProducer());
        editYearMovie.setText("Year: " + String.valueOf(movie.getYear()));
        editGenreMovie.setText("Genre" + movie.getGenre());
        editStorylineMovie.setText("Storyline: " + movie.getStoryline());
    }
}
