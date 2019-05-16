package com.example.mohamed.capstoneapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonList {
    @SerializedName("results")
    ArrayList<Person> people;

    public ArrayList<Person> getPeople() {
        return people;
    }
}
