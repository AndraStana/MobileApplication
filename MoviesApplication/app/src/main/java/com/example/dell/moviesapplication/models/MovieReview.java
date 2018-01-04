package com.example.dell.moviesapplication.models;

import com.example.dell.moviesapplication.enums.Review;

import java.util.UUID;

/**
 * Created by dell on 12/7/2017.
 */

public class MovieReview {
    private String id;
    private String movieTitle;
    private Review review;

    public MovieReview(String id, String movieTitle, Review review) {
        this.id=id;
        this.movieTitle = movieTitle;
        this.review = review;
    }
    public MovieReview() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
