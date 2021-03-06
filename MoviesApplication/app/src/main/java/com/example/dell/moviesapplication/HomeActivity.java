package com.example.dell.moviesapplication;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
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
import com.example.dell.moviesapplication.observer.Observer;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements Observer {

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

        RemoteMovieServiceImpl.attach(this);

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


    @Override
    public void update() {
        Log.d(TAG, "OOOOOOBSEEEEEEEEEEEEEEEEEERVAAAAAAAABLE");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_mood_bad)
                        .setContentTitle("Movie Application")
                        .setContentText( "The list of movies has been modified");

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
