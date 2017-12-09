package com.example.dell.moviesapplication.models;

import com.example.dell.moviesapplication.enums.Review;

/**
 * Created by dell on 12/7/2017.
 */

public class MovieReview {
    private int id;
    private int movieId;
    private Review review;

    public MovieReview(int id, int movieId, Review review) {
        this.id=id;
        this.movieId = movieId;
        this.review = review;
    }
    public MovieReview() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
