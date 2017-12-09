package com.example.dell.moviesapplication.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dell.moviesapplication.db.DatabaseHandler;
import com.example.dell.moviesapplication.enums.Review;
import com.example.dell.moviesapplication.models.Movie;
import com.example.dell.moviesapplication.models.MovieReview;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by dell on 12/7/2017.
 */

public class MovieReviewController extends DatabaseHandler {
    public MovieReviewController(Context context) {
        super(context);
    }


    public boolean create(MovieReview movieReview){
        ContentValues values = new ContentValues();

        values.put("MovieId", movieReview.getMovieId());
        values.put("Review", movieReview.getReview().ordinal());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("MovieReviews", null, values) > 0;
        db.close();

        return createSuccessful;

    }
    public List<MovieReview> getReviewsOfMovie(int movieId){
        SQLiteDatabase db = this.getWritableDatabase();


        String sql = "SELECT * FROM MovieReviews WHERE MovieId = " + movieId;

        List<MovieReview> recordsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                int movieIdd = Integer.parseInt(cursor.getString(cursor.getColumnIndex("MovieId")));
                int review = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Review")));

                MovieReview movieReview = new MovieReview(id, movieIdd, Review.values()[review]);
                recordsList.add(movieReview);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recordsList;
    }
}
