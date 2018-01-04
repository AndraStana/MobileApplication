package com.example.dell.moviesapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dell.moviesapplication.adapters.MoviesAdapter;
import com.example.dell.moviesapplication.listeners.OnClickListenerAddMovie;
import com.example.dell.moviesapplication.listeners.OnClickListenerSendEmail;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RemoteMovieServiceImpl.RemoteMovieServiceInterface remoteService = RemoteMovieServiceImpl.getInstance();

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreateStudent = (Button) findViewById(R.id.buttonAddMovie);

        buttonCreateStudent.setVisibility(View.INVISIBLE);

        Button buttonSendEmail = (Button) findViewById(R.id.sendEmailButton);
        buttonSendEmail.setOnClickListener(new OnClickListenerSendEmail());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "ooooooooooooooooooooooooooo    Logged In USER: " + currentUser.getEmail());

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });


        readRecords();
    }


    public void displayData(Map<String, Movie> movies) {
        ArrayList<Movie> moviesArray = new ArrayList<>();

        for (Map.Entry<String, Movie> entry : movies.entrySet()) {
            moviesArray.add(entry.getValue());
        }

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, moviesArray,false);
        ListView listView = (ListView) findViewById(R.id.moviesListView);
        listView.setAdapter(moviesAdapter);
    }

    public void readRecords() {
        Call<Map<String, Movie>> call = remoteService.getAllMovies();
        call.enqueue(new Callback<Map<String, Movie>>() {
            @Override
            public void onResponse(
                    final Call<Map<String, Movie>> call,
                    final Response<Map<String, Movie>> response)
            {
                final Map<String, Movie> movies = response.body();
                if (movies != null && !movies.isEmpty()) {
                    displayData(movies);
                    Log.d(TAG, "******************onResponse: Movies found as map with size: " + movies.size());
                } else {
                    Log.d(TAG, "******************onResponse: No movies found");
                }
            }

            @Override
            public void onFailure(
                    final Call<Map<String, Movie>> call,
                    final Throwable t) {
                Log.e(TAG, "**********************onResume: Failed to find movies..." + t.getLocalizedMessage(), t);
            }
        });
    }


}
