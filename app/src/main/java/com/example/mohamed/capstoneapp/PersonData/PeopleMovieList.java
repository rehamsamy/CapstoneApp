package com.example.mohamed.capstoneapp.PersonData;

import com.example.mohamed.capstoneapp.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PeopleMovieList {

    @SerializedName("known_for")
    ArrayList<Movie> movies ;

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
