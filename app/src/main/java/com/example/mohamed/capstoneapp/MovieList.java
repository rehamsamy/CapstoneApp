package com.example.mohamed.capstoneapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieList {

    @SerializedName("results")

    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
