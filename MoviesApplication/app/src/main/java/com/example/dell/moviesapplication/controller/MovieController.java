package com.example.dell.moviesapplication.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dell.moviesapplication.db.DatabaseHandler;
import com.example.dell.moviesapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 10/29/2017.
 */

public class MovieController extends DatabaseHandler {
    public MovieController(Context context) {
        super(context);
    }

    public boolean create(Movie movie) {

        ContentValues values = new ContentValues();

        values.put("Title", movie.getTitle());
        values.put("Producer", movie.getProducer());
        values.put("Year", movie.getYear());
        values.put("Genre", movie.getGenre());
        values.put("Storyline", movie.getStoryline());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("movies", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM movies";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<Movie> read() {

        List<Movie> recordsList = new ArrayList<Movie>();
        String sql = "SELECT * FROM Movies ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                int year = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Year")));

                String producer = cursor.getString(cursor.getColumnIndex("Producer"));
                String title = cursor.getString(cursor.getColumnIndex("Title"));

                String genre = cursor.getString(cursor.getColumnIndex("Genre"));
                String storyline = cursor.getString(cursor.getColumnIndex("Storyline"));

                Movie movie = new Movie(id, title, producer, year, genre, storyline);
                recordsList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Movie readSingleRecord(int movieId) {

        //ObjectStudent objectStudent = null;
        Movie movie = null;
        String sql = "SELECT * FROM movies WHERE id = " + movieId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            int year = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Year")));
            String producer = cursor.getString(cursor.getColumnIndex("Producer"));
            String title = cursor.getString(cursor.getColumnIndex("Title"));
            String genre = cursor.getString(cursor.getColumnIndex("Genre"));
            String storyline = cursor.getString(cursor.getColumnIndex("Storyline"));

            movie = new Movie(id, title, producer, year, genre, storyline);
        }
        cursor.close();
        db.close();
        return movie;
    }


    public boolean update(Movie movie) {

        ContentValues values = new ContentValues();

        values.put("Title", movie.getTitle());
        values.put("Producer", movie.getProducer());
        values.put("Year", movie.getYear());
        values.put("Genre", movie.getGenre());
        values.put("Storyline", movie.getStoryline());

        String where = "id = ?";
        String[] whereArgs = {Integer.toString(movie.getId())};
        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("movies", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("movies", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }


}