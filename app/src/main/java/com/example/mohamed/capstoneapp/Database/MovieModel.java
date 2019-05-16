package com.example.mohamed.capstoneapp.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mohamed.capstoneapp.Movie;

import java.util.List;

public class MovieModel extends AndroidViewModel {

    LiveData<List<Movie>> listLiveData;
    public MovieModel(@NonNull Application application) {
        super(application);
        MovieDatabase database=MovieDatabase.getInstance(getApplication());
        listLiveData=database.taskDao().getMovieTasks();

    }


    public LiveData<List<Movie>> getListLiveData() {
        return listLiveData;
    }
}
