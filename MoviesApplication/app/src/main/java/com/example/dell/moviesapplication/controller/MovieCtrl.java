package com.example.dell.moviesapplication.controller;

import android.content.Context;
import android.util.Log;

import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.services.RemoteMovieServiceImpl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dell on 12/30/2017.
 */

public class MovieCtrl {

    private RemoteMovieServiceImpl.RemoteMovieServiceInterface remoteService=RemoteMovieServiceImpl.getInstance();
    private static final String TAG = "MyActivity";
    private Map<String, Movie> moviesToReturn;


    public MovieCtrl() {
    }

//
//    public Map<String, Movie> getAllMovies(){
//        Call<Map<String, Movie>> call = remoteService.getAllMovies();
//        call.enqueue(new Callback<Map<String, Movie>>() {
//            @Override
//            public void onResponse(
//                    final Call<Map<String, Movie>> call,
//                    final Response<Map<String, Movie>> response) {
//                        final Map<String, Movie> movies = response.body();
//                        if (movies != null && !movies.isEmpty()) {
//                            Log.d(TAG, "******************onResponse: Movies found as map with size: " + movies.size());
//                            moviesToReturn=movies;
//                            callback.onSuccess(moviesToReturn);
//
//                        } else {
//                            Log.d(TAG, "******************onResponse: No movies found");
//                            moviesToReturn=new HashMap<String, Movie>();
//                        }
//            }
//
//            @Override
//            public void onFailure(
//                    final Call<Map<String, Movie>> call,
//                    final Throwable t) {
//                    moviesToReturn=new HashMap<String, Movie>();
//                    Log.e(TAG, "**********************onResume: failed to find movies..." + t.getLocalizedMessage() , t);
//            }
//        });
//        return moviesToReturn;
//    }


}
