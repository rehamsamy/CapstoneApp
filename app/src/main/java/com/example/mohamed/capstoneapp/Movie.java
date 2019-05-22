package com.example.mohamed.capstoneapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "task")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int idTable;


    @SerializedName("original_title")
    String original_title;

    @SerializedName("vote_average")
    String vote_average;

    @SerializedName("poster_path")
    String poster_path;

    @SerializedName("overview")
    String overview;

    @SerializedName("release_date")
    String release_date;

    @SerializedName("id")
    String id;



    public Movie(int idTable, String original_title, String vote_average, String poster_path, String overview, String release_date,String id) {
        this.idTable=idTable;
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id=id;

    }

    @Ignore
    public Movie( String original_title, String vote_average, String poster_path, String overview, String release_date,String id) {
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id=id;

    }


    @Ignore
    protected Movie(Parcel in) {
        idTable = in.readInt();
        original_title = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readString();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idTable);
        dest.writeString(original_title);
        dest.writeString(vote_average);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(id);

    }


    public int getIdTable() {
        return idTable;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(String id) {
        this.id = id;
    }
}