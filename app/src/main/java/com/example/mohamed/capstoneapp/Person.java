package com.example.mohamed.capstoneapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Person implements Parcelable {

    @SerializedName("profile_path")
    String profile_path;

    @SerializedName("name")
    String name;

    @SerializedName("known_for")
    ArrayList<Movie> peopleMovie;

    protected Person(Parcel in) {
        profile_path = in.readString();
        name = in.readString();
        peopleMovie = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getProfile_path() {
        return profile_path;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movie> getPeopleMovie() {
        return peopleMovie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profile_path);
        dest.writeString(name);
        dest.writeTypedList(peopleMovie);
    }
}
