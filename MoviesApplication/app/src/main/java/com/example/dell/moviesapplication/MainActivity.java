package com.example.dell.moviesapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.moviesapplication.controller.MovieController;
import com.example.dell.moviesapplication.listeners.OnClickListenerAddMovie;
import com.example.dell.moviesapplication.listeners.OnClickListenerSendEmail;
import com.example.dell.moviesapplication.listeners.OnLongClickListenerMovieRecord;
import com.example.dell.moviesapplication.models.Movie;

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

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<Movie> movies = new MovieController(this).read();

        if (movies.size() > 0) {

            for (Movie obj : movies) {

                int id= obj.getId();
                String movieTitle = obj.getTitle();
                String movieProducer = obj.getProducer();

                String textViewContents = movieTitle + " - " + movieProducer;

                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerMovieRecord());
                linearLayoutRecords.addView(textViewStudentItem);
            }

        } else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}