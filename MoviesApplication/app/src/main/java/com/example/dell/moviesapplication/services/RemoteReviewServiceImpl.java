package com.example.dell.moviesapplication.services;

import com.example.dell.moviesapplication.BuildConfig;
import com.example.dell.moviesapplication.models.MovieReview;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dell on 1/3/2018.
 */

public class RemoteReviewServiceImpl {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // use gson converter
            .build();

    private static RemoteReviewServiceImpl.RemoteReviewServiceInterface service = null;

    public static RemoteReviewServiceImpl.RemoteReviewServiceInterface getInstance() {
        if (service == null) {
            service = retrofit.create(RemoteReviewServiceImpl.RemoteReviewServiceInterface.class);
        }
        return service;
    }

    public interface RemoteReviewServiceInterface  {

        @PUT("/review/{id}.json")
        Call<MovieReview> createReview(
                @Path("id") String id,
                @Body MovieReview review);

        @GET("/review/{id}.json")
        Call<MovieReview> getReview(@Path("id") String id);

        @GET("/review/.json")
        Call<Map<String, MovieReview>> getAllReviews();

    }
}
