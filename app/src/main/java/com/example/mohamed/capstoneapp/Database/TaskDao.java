package com.example.mohamed.capstoneapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.mohamed.capstoneapp.Movie;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertMovieTask(Movie movie);

    @Query("SELECT * FROM task ORDER BY idd")
   LiveData<List<Movie>> getMovieTasks();


    @Delete
    void deleteMovieTask(Movie movie);






}
