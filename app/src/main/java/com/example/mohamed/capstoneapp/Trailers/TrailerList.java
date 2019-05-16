package com.example.mohamed.capstoneapp.Trailers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerList {
    @SerializedName("results")
    ArrayList<Trailer> trailers;

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
