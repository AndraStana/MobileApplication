package com.example.dell.moviesapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 11/3/2017.
 */


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "MovieApp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Movies " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Title TEXT, " +
                "Producer TEXT, " +
                "Year int, " +
                "Storyline TEXT, " +
                "Genre TEXT ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Movies";
        db.execSQL(sql);
        onCreate(db);
    }




}