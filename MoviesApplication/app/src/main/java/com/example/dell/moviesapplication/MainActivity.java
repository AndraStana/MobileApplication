package com.example.dell.moviesapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.moviesapplication.adapters.MoviesAdapter;
import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.listeners.OnClickListenerAddMovie;
import com.example.dell.moviesapplication.listeners.OnClickListenerSendEmail;
import com.example.dell.moviesapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieController ctrl = new MovieController(this);


        Button buttonCreateStudent = (Button) findViewById(R.id.buttonAddMovie);
        buttonCreateStudent.setOnClickListener(new OnClickListenerAddMovie(ctrl));

        Button buttonSendEmail = (Button) findViewById(R.id.sendEmailButton);
        buttonSendEmail.setOnClickListener(new OnClickListenerSendEmail());
        readRecords();

    }


    public void readRecords() {
       List<Movie> movies = new MovieController(this).read();
        ArrayList<Movie> moviesArray = new ArrayList<>();

        for (Movie movie : movies) {
            moviesArray.add(movie);
        }

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, moviesArray);
        ListView listView = (ListView) findViewById(R.id.moviesListView);
        listView.setAdapter(moviesAdapter);


    }
}