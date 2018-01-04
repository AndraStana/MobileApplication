package com.example.dell.moviesapplication.services;

import com.example.dell.moviesapplication.BuildConfig;
import com.example.dell.moviesapplication.models.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dell on 12/29/2017.
 */


public class RemoteMovieServiceImpl {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // use gson converter
            .build();

    private static RemoteMovieServiceInterface service = null;

    public static RemoteMovieServiceInterface  getInstance() {
        if (service == null) {
            service = retrofit.create(RemoteMovieServiceInterface.class);
        }
        return service;
    }

    public interface RemoteMovieServiceInterface  {

        @PUT("/movie/{title}.json")
        Call<Movie> createMovie(
                @Path("title") String title,
                @Body Movie movie);

        @GET("/movie/{title}.json")
        Call<Movie> getMovie(@Path("title") String title);

        @GET("/movie/.json")
        Call<Map<String, Movie>> getAllMovies();

        @DELETE("/movie/{title}.json")
        Call<Movie> deleteMovie(@Path("title") String title);

    }
}
