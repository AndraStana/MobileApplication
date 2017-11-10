package com.example.dell.moviesapplication.models;

/**
 * Created by dell on 10/29/2017.
 */

public class Movie {
    private int id;
    private String title;
    private String producer;


    public Movie() {
    }

    private int year;
    private String genre;
    private String storyline;

    public Movie(String title, String producer, int year, String genre, String storyline) {
        this.title = title;
        this.producer = producer;
        this.year = year;
        this.genre = genre;
        this.storyline = storyline;
    }
    public Movie(int id,String title, String producer, int year, String genre, String storyline) {
        this.id=id;
        this.title = title;
        this.producer = producer;
        this.year = year;
        this.genre = genre;
        this.storyline = storyline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String toString(){
        return title + " - " + producer;
    }
}
